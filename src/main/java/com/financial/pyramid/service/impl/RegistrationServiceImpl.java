package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.type.Position;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.service.*;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.service.exception.UserRegistrationException;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.Password;
import com.financial.pyramid.web.form.RegistrationForm;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 21:02
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
    private static final java.util.List<String> AVAILABLE_IMAGE_TYPES = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif");
    private static final int IMAGE_LIMIT = 50000;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    SettingsService settingsService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registration(RegistrationForm form) throws UserRegistrationException, UserAlreadyExistsException {
        Invitation invitation = invitationService.findById(form.getInvitationId());
        if (invitation == null) throw new UserAlreadyExistsException();

        User user = new User();
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setEmail(invitation.getEmail());
        user.setRole(Role.USER);
        user.setPhoto(getImage(form));
        setAccount(user);
        setPassport(user, form);
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, LocaleContextHolder.getLocale());
        try {
            user.setDateOfBirth(format.parse(form.getDateOfBirth()));
        } catch (ParseException e) {
            throw new UserRegistrationException("dateOfBirthIncorrect");
        }

        String password = Password.generate();
        user.setPassword(passwordEncoder.encode(password));

        User owner = updateOwner(user, invitation);
        if (owner.getId().equals(invitation.getParent().getId())) {
            invitation.setParent(owner);
        }
        User parent = updateParent(user, invitation);

        userService.save(user);
        payment(owner, parent, user);
        userService.save(user);

        // отправка пароля после удачной регистрации
        if (!emailService.sendPassword(user.getName(), password, user.getEmail()))
            throw new UserRegistrationException("serviceIsNotAvailable");

        invitationService.delete(invitation);
        logger.info("User registered! id: " + user.getId() + "; email: " + user.getEmail());
        return true;
    }

    private String getImage(RegistrationForm r) {
        if (r.getPhoto() == null || r.getPhoto().isEmpty()) return null;
        try {
            String type = r.getPhoto().getContentType();
            if (!AVAILABLE_IMAGE_TYPES.contains(type)) {
                throw new UserRegistrationException("imageTypeIncorrect");
            }
            if (r.getX() == null || r.getY() == null || r.getW() == null || r.getH() == null) {
                return "data:" + type + ";base64," + Base64.encode(r.getPhoto().getBytes());
            }
            BufferedImage bufferedImage = ImageIO.read(r.getPhoto().getInputStream());
            BufferedImage resizeImage = bufferedImage.getSubimage(r.getX(), r.getY(), r.getW(), r.getH());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(resizeImage, type.replace("image/", ""), os);
            if (os.size() > IMAGE_LIMIT) {
                throw new UserRegistrationException("imageIsTooLarge");
            }
            return "data:" + r.getPhoto().getContentType() + ";base64," + Base64.encode(os.toByteArray());
        } catch (IOException e) {
            logger.info("File not loaded! User: " + r.getName() + "; message: " + e.getMessage());
        }
        return null;
    }

    private User findFirstInactiveUser(User user) {
        if (user == null) return null;
        User inactiveUser = null;
        User next = user;
        while (next.getCountInvitedUsers() == 0) {
            inactiveUser = next;
            next = userService.findParent(next.getId());
        }
        return inactiveUser;
    }

    // выставляет участнику user, родителя parent, обновляя связи
    // возвращает общего родителя
    private User setParentForUser(User user, User parent) {
        User userParent = userService.findParent(user.getId());
        // user уже имеет родителя
        if (userParent != null) {
            // parent имеет свох детей, передаем их его родителю
            if (parent.getId() != null) {
                User parentParent = userService.findParent(parent.getId());
                if (parentParent.getLeftChild() != null && parentParent.getLeftChild().getId().equals(parent.getId())) {
                    parentParent.setLeftChild(parent.getLeftChild());
                } else {
                    parentParent.setRightChild(parent.getRightChild());
                }
            }
            // выставляем user детем parent а parent детем userParent
            if (userParent.getLeftChild() != null && userParent.getLeftChild().getId().equals(user.getId())) {
                parent.setLeftChild(user);
                userParent.setLeftChild(parent);
            } else {
                parent.setRightChild(user);
                userParent.setRightChild(parent);
            }
            // так как начиная с parent, уровни у низстоящих пользователей поменялись, то инкрементим
            parent.setLevel(userParent.getLevel());
            incrementLevel(parent);
        }
        return userParent;
    }

    private void setUserToPosition(User user, User parent, Position position) {
        // если место уже занято, ищем первое свободное
        if (position.equals(Position.LEFT)) {
            while (parent.getLeftChild() != null && parent.getLeftChild().getCountInvitedUsers() != 0) {
                parent = parent.getLeftChild();
            }
            user.setLeftChild(parent.getLeftChild());
            parent.setLeftChild(user);
        } else {
            while (parent.getRightChild() != null && parent.getRightChild().getCountInvitedUsers() != 0) {
                parent = parent.getRightChild();
            }
            user.setRightChild(parent.getRightChild());
            parent.setRightChild(user);
        }
    }

    private User updateParent(User user, Invitation invitation) {
        User parent = invitation.getParent();
        User inactiveUserOverParent = findFirstInactiveUser(parent);

        /* если есть неактивные участники над родителем
        * поднимаем нового участника до первого активного
        * иначе ставим в запланирванную позицию
        * */
        if (inactiveUserOverParent != null) {
            parent = setParentForUser(inactiveUserOverParent, user);
        } else {
            setUserToPosition(user, parent, invitation.getPosition());
            user.setLevel(parent.getLevel() + 1);
        }

        return parent;
    }

    private User updateOwner(User user, Invitation invitation) {
        User owner = invitation.getSender();
        User parent = userService.findParent(owner.getId());
        User inactiveUserOverOwner = findFirstInactiveUser(parent);

        /* если есть неактивные участники над приглашающим
        * поднимаем приглашаущего до первого активного участника
        * */
        if (inactiveUserOverOwner != null) {
            setParentForUser(inactiveUserOverOwner, owner);
        }
        user.setOwnerId(owner.getId());
        owner.setCountInvitedUsers(owner.getCountInvitedUsers() + 1);
        return owner;
    }

    private void payment(User owner, User parent, User user) {
        Double costByPersonalUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_PERSONAL_USER));
        owner.getAccount().writeIN(costByPersonalUser);

        Double maxLevelForPayment = Double.parseDouble(settingsService.getProperty(Setting.MAX_LEVEL_FOR_PAYMENT));
        Double costByUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_USER));
        User parentForPay = parent;
        while (parentForPay != null && (user.getLevel() - parentForPay.getLevel()) <= maxLevelForPayment) {
            if (parentForPay.getCountInvitedUsers() >= 2 && !parentForPay.getAccount().isLocked()) {
                parentForPay.getAccount().writeIN(costByUser);
            }
            parentForPay = userService.findParent(parentForPay.getId());
        }
    }

    private void setAccount(User user) {
        Account account = new Account();
        Calendar calendar = Calendar.getInstance();
        account.setDateActivated(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        account.setDateExpired(calendar.getTime());
        account.writeIN(0D);
        user.setAccount(account);
    }

    private void setPassport(User user, RegistrationForm form) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, LocaleContextHolder.getLocale());
        Passport passport = new Passport();
        passport.setSerial(form.getPassportSerial());
        passport.setNumber(form.getPassportNumber());
        passport.setIssuedBy(form.getPassportIssuedBy());
        passport.setRegisteredAddress(form.getRegisteredAddress());
        passport.setResidenceAddress(form.getResidenceAddress());

        if (form.getPassportDate() != null && !form.getPassportDate().isEmpty()) {
            try {
                passport.setDate(format.parse(form.getPassportDate()));
            } catch (ParseException e) {
                logger.info("User passport date is not set. Email: " + user.getEmail());
            }
        }

        user.setPassport(passport);
    }

    private void incrementLevel(User user) {
        user.setLevel(user.getLevel() + 1);
        if (user.getLeftChild() != null) incrementLevel(user.getLeftChild());
        if (user.getRightChild() != null) incrementLevel(user.getRightChild());
    }
}
