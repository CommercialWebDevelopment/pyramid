package com.financial.pyramid.dao;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.QueryForm;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:13
 */
public interface UserDao extends AbstractDao<User, Long> {

    public List<User> findByName(String name);

    public List<User> findByLogin(String login);

    public List<User> findByNamePassword(String name, String password);

    public User findByGlobalId(String globalId);

    public boolean isLogin(String login);

    public List<User> findByQuery(QueryForm form);

}
