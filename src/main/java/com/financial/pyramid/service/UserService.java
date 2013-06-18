package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.exception.UserConfirmOverdueException;
import com.financial.pyramid.service.exception.UserNotFoundException;
import com.financial.pyramid.web.form.QueryForm;
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

    public boolean checkLogin(String login);

    public List<User> findByName(String userName);

    public List<User> findByLogin(String login);

    public List<User> findByNamePassword(String name, String password);

    public User findByGlobalId(String globalId);

    public User confirm(String globalId) throws UserNotFoundException, UserConfirmOverdueException;

    public List<User> list();

    public List<User> findByQuery(QueryForm form);
}
