package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.Role;
import com.financial.pyramid.domain.User;
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
    public static final int CONFIRM_PERIOD = 604800000;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findByName(String userName) {
        return userDao.findByName(userName);
    }

    @Override
    public List<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void deleteUser(Long id) {
        User user = userDao.findById(id);
        userDao.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean checkLogin(String login) {
        return userDao.isLogin(login);
    }

    @Override
    public List<User> findByNamePassword(String name, String password) {
        return userDao.findByNamePassword(name, password);
    }

    @Override
    public User findByGlobalId(String globalId) {
        return userDao.findByGlobalId(globalId);
    }

    @Override
    @Transactional(readOnly = false)
    public User confirm(String globalId) throws UserNotFoundException, UserConfirmOverdueException {
        User user = findByGlobalId(globalId);
        if (user == null) throw new UserNotFoundException();
        if (user.getConfirmed()) return user;
        if ((System.currentTimeMillis() - user.getCreated().getTime()) > CONFIRM_PERIOD)
            throw new UserConfirmOverdueException();
        user.setConfirmed(true);
        return userDao.merge(user);
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
    public void updateUser(RegistrationForm form) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        User user = findById(Long.parseLong(form.getId()));
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setLogin(form.getLogin());
        user.setEmail(form.getEmail());
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
        saveUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = findByLogin(username);
        if (users.size() == 1 && users.get(0).getConfirmed()) {
            User user = users.get(0);
            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(users.get(0).getRole().name())));
        } else {
            throw new UsernameNotFoundException("User with name "+username+" not found");
        }
    }

    @Override
    public BinaryTree getUserBinaryTree(User user) {
        return new BinaryTree<UserForm>(
                24744353L,
                new UserForm("Вася", "Васильев", "1112313"),
                new BinaryTree<UserForm>(
                        645456L,
                        new UserForm("Иван", "Иванов", "1234234"),
                        new BinaryTree<UserForm>(
                                534534L,
                                new UserForm("Петор", "Петров", "646465"),
                                new BinaryTree<UserForm>(
                                        13242L,
                                        new UserForm("Евгений", "Евгениев", "13453"),
                                        null,
                                        new BinaryTree<UserForm>(982131L, new UserForm("Степан", "Степанов", "14456"))
                                ),
                                new BinaryTree<UserForm>(3424254L, new UserForm("Дмитрий", "Дмитриев", "123423"))
                        ),
                        new BinaryTree<UserForm>(978132L, new UserForm("Алексадр", "Александров", "2342342"))
                ),
                new BinaryTree<UserForm>(856645L, new UserForm("Максим", "Максимов", "1"))
        );
    }
}
