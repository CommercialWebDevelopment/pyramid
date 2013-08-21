package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public boolean isEmail(String email) {
        return getCount(Restrictions.eq("login", email)) > 0;
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = findByCriteria(Restrictions.eq("email", email));
        return users.size() == 0 ? null : users.get(0);
    }

    @Override
    public List<User> findByQuery(QueryForm form) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.setCacheable(true)
                .add(Restrictions.not(Restrictions.eq("role", Role.SUPER_ADMIN)))
                .addOrder(form.order.equals("asc") ? Order.asc(form.sort) : Order.desc(form.sort))
                .setFirstResult((form.page - 1) * form.count)
                .setMaxResults(form.count);
        if (!form.query.isEmpty()) {
            criteria.add(Restrictions.like(form.field, form.query));
        }
        return (List<User>) criteria.list();
    }
}