package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.UserConfirmOverdueException;
import com.financial.pyramid.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.financial.pyramid.dao.*;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:17
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    public static final String SALT = "user";
    public static final int CONFIRM_PERIOD = 604800000;

    @Autowired
    private UserDao userDao;

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
        if(user == null) throw new UserNotFoundException();
        if(user.getConfirmed()) return user;
        if((System.currentTimeMillis() - user.getCreated().getTime()) > CONFIRM_PERIOD) throw new UserConfirmOverdueException();
        user.setConfirmed(true);
        return userDao.merge(user);
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }
}
