package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:15
 */
public interface UserService {

    public List<User> findByName(String userName);

    public void saveUser(User user);

    public void deleteUser(Long id);

    public User findById(Long id);
}
