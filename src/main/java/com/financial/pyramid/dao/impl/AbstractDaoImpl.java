package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:07
 */
public abstract class AbstractDaoImpl <E, I extends Serializable> implements AbstractDao<E, I> {
    private Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }

    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return (List<E>) criteria.list();
    }

    @Override
    public List<E> findAll() {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        return (List<E>) criteria.list();
    }

    @Override
    public Long getCount(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }
}
