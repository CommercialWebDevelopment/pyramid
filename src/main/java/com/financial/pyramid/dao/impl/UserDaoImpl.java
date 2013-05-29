package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.dao.impl.AbstractDaoImpl;
import com.financial.pyramid.domain.User;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:12
 */
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {

    protected UserDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> findByName(String name) {
        return findByCriteria(Restrictions.eq("name", name));
    }
}