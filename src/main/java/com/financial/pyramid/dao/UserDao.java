package com.financial.pyramid.dao;

import com.financial.pyramid.domain.User;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:13
 */
public interface UserDao extends AbstractDao<User, Long> {

    public List<User> findByName(String name);

}
