package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.exception.UserConfirmOverdueException;
import com.financial.pyramid.service.exception.UserNotFoundException;
import com.financial.pyramid.web.tree.BinaryTree;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:15
 */
public interface UserService extends UserDetailsService {

    public void saveUser(User user);

    public void deleteUser(Long id);

    public User findById(Long id);

    public boolean checkLogin(String login);

    public List<User> findByName(String userName);

    public List<User> findByLogin(String login);

    public List<User> findByEmail(String email);

    public List<User> findByNamePassword(String name, String password);

    public User findByGlobalId(String globalId);

    public User confirm(String globalId) throws UserNotFoundException, UserConfirmOverdueException;

    public List<User> list();

    public List<User> findByQuery(QueryForm form);

    public void updateUser(RegistrationForm form);

    public BinaryTree getUserBinaryTree(User user);

    public String createPassword(int number);
}
