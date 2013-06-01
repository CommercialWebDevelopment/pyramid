package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:15
 */
public interface UserService {

    public void saveUser(User user);

    @Secured("ROLE_USER")
    public void deleteUser(Long id);

    public User findById(Long id);

    public List<User> findByName(String userName);

    public List<User> findByNamePassword(String name, String password);
}
