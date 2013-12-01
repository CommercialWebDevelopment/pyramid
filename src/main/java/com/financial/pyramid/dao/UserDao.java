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

    public boolean isEmail(String email);

    public List<User> findByQuery(QueryForm form);

    public User findByEmail(String email);

    public List<User> findByOwner(Long ownerId);

    public User findParent(Long userId);

    public Long getCountUsersOnLevel(Integer level);

    public Long getCountUsersWithURI(String uri);
}
