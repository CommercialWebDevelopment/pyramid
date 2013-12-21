package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
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

    @Override
    public List<User> findByOwner(Long ownerId) {
        return findByCriteria(Restrictions.eq("ownerId", ownerId));
    }

    @Override
    public User findParent(Long userId) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.or(Restrictions.eq("leftChild.id", userId), Restrictions.eq("rightChild.id", userId)));
        List<User> list = (List<User>)criteria.list();
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Long getCountUsersOnLevel(Integer level) {
        return getCount(Restrictions.eq("level", level));
    }

    @Override
    public Long getCountUsersWithURI(String uri) {
        return getCount(Restrictions.ilike("uri", uri, MatchMode.START));
    }

    @Override
    public Long getCountInvitedUsersWithURI(String uri, Long ownerId) {
        return getCount(Restrictions.and(Restrictions.eq("ownerId", ownerId), Restrictions.ilike("uri", uri, MatchMode.START)));
    }
}