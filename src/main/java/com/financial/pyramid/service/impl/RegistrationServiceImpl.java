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

        User parent = findParent(invitation);
        user.setLevel(parent.getLevel() + 1);
        setOwner(user, invitation.getSender());
        setParent(user, invitation, parent);

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
            logger.error("File not loaded! User: " + r.getName() + "; message: " + e.getMessage());
        }
        return null;
    }

    private User findParent(Invitation invitation) {
        User parent = invitation.getParent();

        /*
        если родитель ни кого не пригласил
        и аккаунт не активирован
        и это не тот кто пригласил
        перепрыгиваем его
        */
        while (userService.findUsersByOwner(parent.getId()).size() == 0
                && parent.getAccount().isLocked()
                && !invitation.getSender().getId().equals(parent.getId())) {
            parent = userService.findParent(parent.getId());
        }

        if (invitation.getPosition().equals(Position.LEFT)) {
            /*
            если место уже занято, ищем первое свободное по левой ноге
            пока в левой ноге кто то есть
            и он уже кого то пригласил или активирован аккаунт
            */
            while (parent.getLeftChild() != null
                    && (userService.findUsersByOwner(parent.getLeftChild().getId()).size() != 0
                    || !parent.getLeftChild().getAccount().isLocked())) {
                parent = parent.getLeftChild();
            }
        } else {  // то же самое для правой ноги
            while (parent.getRightChild() != null
                    && (userService.findUsersByOwner(parent.getRightChild().getId()).size() != 0
                    || !parent.getRightChild().getAccount().isLocked())) {
                parent = parent.getRightChild();
            }
        }
        return parent;
    }

    private void setParent(User user, Invitation invitation, User parent) {
        if (invitation.getPosition().equals(Position.LEFT)) {
            if (parent.getLeftChild() != null) {
                user.setLeftChild(parent.getLeftChild());
                incrementLevel(parent.getLeftChild());
            }
            parent.setLeftChild(user);
        } else {
            if (parent.getRightChild() != null) {
                user.setRightChild(parent.getRightChild());
                incrementLevel(parent.getRightChild());
            }
            parent.setRightChild(user);
        }
        // если пригласившии не является родителем
        if (!invitation.getSender().getId().equals(parent.getId())) {
            Double costByUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_USER));
            parent.getAccount().writeIN(costByUser);
        }
    }

    private void setOwner(User user, User owner) {
        Double maxLevelForPayment = Double.parseDouble(settingsService.getProperty(Setting.MAX_LEVEL_FOR_PAYMENT));
        // если за этого пользователя положена оплата
        if ((user.getLevel() - owner.getLevel()) <= maxLevelForPayment) {
            Double costByUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_PERSONAL_USER));
            owner.getAccount().writeIN(costByUser);
            Long count = userService.getCountUsersOnLevel(user.getLevel());
            // если уровень заполнен
            if (count.equals(user.getLevel().longValue())) {
                Double costByCompletedLevel = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_COMPLETED_LEVEL));
                owner.getAccount().writeIN(costByCompletedLevel);
            }
        }
        user.setOwnerId(owner.getId());
    }

    private void setAccount(User user) {
        Account account = new Account();
        Calendar calendar = Calendar.getInstance();
        account.setDateActivated(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        account.setDateExpired(calendar.getTime());
        account.setLocked(true);
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
                logger.error("User passport date is not set. Email: " + user.getEmail());
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
