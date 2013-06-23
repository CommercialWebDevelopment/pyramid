package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:12
 */
@Repository(value = "userDao")
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {

    protected UserDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> findByName(String name) {
        return findByCriteria(Restrictions.eq("name", name));
    }

    @Override
    public List<User> findByLogin(String login) {
        return findByCriteria(Restrictions.eq("login", login));
    }

    @Override
    public List<User> findByNamePassword(String name, String password) {
        Map map = new HashMap();
        map.put("name", name);
        map.put("password", password);
        return findByCriteria(Restrictions.allEq(map));
    }

    @Override
    public User findByGlobalId(String globalId) {
        List<User> users = findByCriteria(Restrictions.eq("globalId", globalId));
        assert users.size() > 1;
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public boolean isLogin(String login) {
        return getCount(Restrictions.eq("login", login)) > 0;
    }

    @Override
    public List<User> findByQuery(QueryForm form) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.setCacheable(true)
                .addOrder(form.order.equals("asc") ? Order.asc(form.sort) : Order.desc(form.sort))
                .setFirstResult((form.page - 1) * form.count)
                .setMaxResults(form.count);
        if(!form.query.isEmpty()){
            criteria.add(Restrictions.like(form.field, form.query));
        }
        return (List<User>) criteria.list();
    }
}