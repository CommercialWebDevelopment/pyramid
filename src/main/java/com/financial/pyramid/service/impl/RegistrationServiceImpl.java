package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Passport;
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
    public boolean registration(RegistrationForm form) throws UserAlreadyExistsException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        User newUser = new User();
        newUser.setName(form.getName());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        newUser.setSurname(form.getSurname());
        newUser.setPatronymic(form.getPatronymic());
        newUser.setPhoneNumber(form.getPhoneNumber());
        newUser.setConfirmed(false);
        newUser.setGlobalId(passwordEncoder.encode(form.getLogin()));
        newUser.setLogin(form.getLogin());
        newUser.setEmail(form.getEmail());

        try {
            Date date = format.parse(form.getDateOfBirth());
            newUser.setDateOfBirth(date);
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
        newUser.setPassport(passport);
        boolean sent = emailService.sendToUser(newUser);
        if(!sent) return false;
        userService.saveUser(newUser);
        return true;
    }
}
