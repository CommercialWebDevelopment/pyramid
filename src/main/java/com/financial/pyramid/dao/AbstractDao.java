package com.financial.pyramid.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:08
 */
public interface AbstractDao<E, I> {

    public E findById(I id);

    public void saveOrUpdate(E e);

    public E merge(E e);

    public void delete(E e);

    public List<E> findByCriteria(Criterion criterion);

    public List<E> findAll();

    public Long getCount(Criterion criterion);

    public Query createQuery(String query);

}
