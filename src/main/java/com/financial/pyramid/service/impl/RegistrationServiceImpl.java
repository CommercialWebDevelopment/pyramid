package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.Role;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 21:02
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static Logger logger = Logger.getLogger(RegistrationServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public boolean registration(RegistrationForm form, boolean confirm) throws UserAlreadyExistsException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        User user = new User();
        user.setName(form.getName());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setConfirmed(!confirm);
        user.setGlobalId(passwordEncoder.encode(form.getLogin()));
        user.setLogin(form.getLogin());
        user.setEmail(form.getEmail());
        user.setRole(Role.USER);

        try {
            Date date = format.parse(form.getDateOfBirth());
            user.setDateOfBirth(date);
        } catch (ParseException e) {
            logger.error("User date of birth is not set");
        }

        Passport passport = new Passport();
        passport.setSerial(form.getPassportSerial());
        passport.setNumber(form.getPassportNumber());
        passport.setIssuedBy(form.getPassportIssuedBy());
        passport.setRegisteredAddress(form.getRegisteredAddress());
        passport.setResidenceAddress(form.getResidenceAddress());

        try {
            Date date = format.parse(form.getPassportDate());
            passport.setDate(date);
        } catch (ParseException e) {
            logger.error("User passport date is not set");
        }
        user.setPassport(passport);
        if(confirm) {
            boolean sent = emailService.sendToUser(user);
            if(!sent) return false;
        }
        userService.saveUser(user);
        return true;
    }
}
