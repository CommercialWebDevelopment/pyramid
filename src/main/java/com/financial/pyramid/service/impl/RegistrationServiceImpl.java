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
    private static final String BONUS_FOR_PERSONAL_USER = "bonus_for_personal_user";
    private static final String BONUS_FOR_USER = "bonus_for_user";
    private static final String ACCOUNT_CREATED = "account_created";

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
        User parent = updateParent(user, invitation);
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

    private User updateParent(User user, Invitation invitation) {
        User parent = invitation.getParent();
        if (invitation.getPosition().equals(Position.LEFT)) {
            while (parent.getLeftChild() != null) {
                parent = parent.getLeftChild();
            }
            user.setUri(parent.getUri() + User.LEFT);
            parent.setLeftChild(user);
        } else {
            while (parent.getRightChild() != null) {
                parent = parent.getRightChild();
            }
            user.setUri(parent.getUri() + User.RIGHT);
            parent.setRightChild(user);
        }
        user.setLevel(parent.getLevel() + 1);
        user.setParent(parent);
        return parent;
    }

    private User updateOwner(User user, Invitation invitation) {
        User owner = invitation.getSender();
        User ownerPositionBefore = null;
        // если owner еще ни кого не пригласил, поднимаем до первого активного
        if (owner.getCountInvitedUsers() == 0) {
            // если owner не админ
            if (owner.getParent() != null) {
                User parent = owner.getParent();
                while (parent.getCountInvitedUsers() == 0) {
                    swapParentWithChild(parent, owner);
                    if (ownerPositionBefore == null) ownerPositionBefore = parent;
                    parent = owner.getParent();
                }
            }
        }
        user.setOwnerId(owner.getId());
        owner.setCountInvitedUsers(owner.getCountInvitedUsers() + 1);
        if (owner.getId().equals(invitation.getParent().getId()) && ownerPositionBefore != null) {
            invitation.setParent(ownerPositionBefore);
        }
        return owner;
    }

    private void swapParentWithChild(User parent, User child) {
        User leftChild = child.getLeftChild();
        User rightChild = child.getRightChild();
        String uri = child.getUri();

        User commonParent = parent.getParent();
        if (commonParent != null) {
            if (commonParent.getLeftChild() != null && commonParent.getLeftChild().getId().equals(parent.getId())) {
                commonParent.setLeftChild(child);
            } else {
                commonParent.setRightChild(child);
            }
            child.setParent(parent.getParent());
        }
        child.setLevel(parent.getLevel());
        parent.setLevel(parent.getLevel() + 1);
        child.setUri(parent.getUri());
        parent.setUri(uri);

        if (parent.getLeftChild() != null && parent.getLeftChild().getId().equals(child.getId())) {
            child.setLeftChild(parent);
            child.setRightChild(parent.getRightChild());
        } else {
            child.setRightChild(parent);
            child.setLeftChild(parent.getLeftChild());
        }
        parent.setLeftChild(leftChild);
        parent.setRightChild(rightChild);

        if (parent.getLeftChild() != null) parent.getLeftChild().setParent(parent);
        if (parent.getRightChild() != null) parent.getRightChild().setParent(parent);

        if (child.getLeftChild() != null) child.getLeftChild().setParent(child);
        if (child.getRightChild() != null) child.getRightChild().setParent(child);
    }

    private void payment(User owner, User parent, User user) {
        Double costByPersonalUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_PERSONAL_USER));
        owner.getAccount().writeIN(costByPersonalUser, BONUS_FOR_PERSONAL_USER, user.getId());

        Double maxLevelForPayment = Double.parseDouble(settingsService.getProperty(Setting.MAX_LEVEL_FOR_PAYMENT));
        Double costByUser = Double.parseDouble(settingsService.getProperty(Setting.COST_BY_USER));
        User parentForPay = parent;
        while (parentForPay != null && (user.getLevel() - parentForPay.getLevel()) <= maxLevelForPayment) {
            if (!parentForPay.getId().equals(owner.getId()) && parentForPay.getCountInvitedUsers() >= 2 && !parentForPay.getAccount().isLocked()) {
                parentForPay.getAccount().writeIN(costByUser, BONUS_FOR_USER, user.getId());
            }
            parentForPay = parentForPay.getParent();
        }
    }

    private void setAccount(User user) {
        Account account = new Account();
        Calendar calendar = Calendar.getInstance();
        account.setDateActivated(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        account.setDateExpired(calendar.getTime());
        account.writeIN(0D, ACCOUNT_CREATED, null);
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
}
