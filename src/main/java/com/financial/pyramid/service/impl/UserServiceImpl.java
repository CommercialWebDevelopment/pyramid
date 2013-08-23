package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.UserConfirmOverdueException;
import com.financial.pyramid.service.exception.UserNotFoundException;
import com.financial.pyramid.web.tree.BinaryTree;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.RegistrationForm;
import com.financial.pyramid.web.form.UserForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:17
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = false)
    public void save(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void delete(Long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.isEmail(email);
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByQuery(QueryForm form) {
        return userDao.findByQuery(form);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void update(RegistrationForm form) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        User user = findById(Long.parseLong(form.getId()));
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setRole(Role.USER);

        try {
            Date date = format.parse(form.getDateOfBirth());
            user.setDateOfBirth(date);
        } catch (ParseException e) {
            logger.error("User date of birth is not set");
        }

        Passport passport = user.getPassport();
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
        save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole().name())));
        } else {
            throw new UsernameNotFoundException("User with name "+username+" not found");
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public BinaryTree getBinaryTree(String email) {
        User user = findByEmail(email);
        return new BinaryTree(user);
    }

    @Override
    public List<User> findUsersByOwner(Long ownerId) {
        return userDao.findByOwner(ownerId);
    }
}
