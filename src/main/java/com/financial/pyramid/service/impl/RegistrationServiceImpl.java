package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 21:02
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean registration(RegistrationForm form) throws UserAlreadyExistsException {
        List<User> users = userService.findByName(form.getName());
        if(users.size() > 0) throw new UserAlreadyExistsException();
        User newUser = new User();
        newUser.setName(form.getName());
        newUser.setPassword(passwordEncoder.encode(form.getPassword()));
        userService.saveUser(newUser);
        return true;
    }
}
