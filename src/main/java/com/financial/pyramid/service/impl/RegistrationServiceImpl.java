package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.type.Position;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.InvitationService;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.SendingMailException;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 21:02
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
    private static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final short PASSWORD_LENGTH = 10;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean registration(RegistrationForm form) throws UserAlreadyExistsException, SendingMailException, ParseException {
        Invitation invitation = invitationService.findById(form.getInvitationId());
        if (invitation == null) throw new UserAlreadyExistsException();

        User user = new User();
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setEmail(invitation.getEmail());
        user.setRole(Role.USER);
        user.setOwnerId(invitation.getSenderId());

        Account account = new Account();
        Calendar calendar = Calendar.getInstance();
        account.setDateActivated(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        account.setDateExpired(calendar.getTime());
        account.setLocked(false);
        account.setEarningsSum(0D);
        account.writeIN(0L);
        user.setAccount(account);

        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, LocaleContextHolder.getLocale());
        user.setDateOfBirth(format.parse(form.getDateOfBirth()));

        Passport passport = new Passport();
        passport.setSerial(form.getPassportSerial());
        passport.setNumber(form.getPassportNumber());
        passport.setIssuedBy(form.getPassportIssuedBy());
        passport.setRegisteredAddress(form.getRegisteredAddress());
        passport.setResidenceAddress(form.getResidenceAddress());

        try {
            passport.setDate(format.parse(form.getPassportDate()));
        } catch (ParseException e) {
            logger.error("User passport date is not set");
        }
        user.setPassport(passport);
        String password = generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);

        // отправка пароля после удачной регистрации
        if (!emailService.sendPassword(user.getName(), password, user.getEmail()))
            throw new SendingMailException();

        // связь с родителем
        User parent = invitation.getParent();
        if (invitation.getPosition().equals(Position.LEFT)) {
            // если место уже занято, ищем первое свободное по левой ноге
            while (parent.getLeftChild() != null)
                parent = parent.getLeftChild();
            parent.setLeftChild(user);
        } else {
            // если место уже занято, ищем первое свободное по правой ноге
            while (parent.getRightChild() != null)
                parent = parent.getRightChild();
            parent.setRightChild(user);
        }

        invitationService.delete(invitation);
        return true;
    }

    private synchronized String generatePassword() {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= PASSWORD_LENGTH; i++) {
            int pos = rand.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(pos));
        }
        return sb.toString();
    }
}
