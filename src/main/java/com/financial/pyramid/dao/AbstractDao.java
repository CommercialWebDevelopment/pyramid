package com.financial.pyramid.dao;

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

    public void delete(E e);

    public List<E> findByCriteria(Criterion criterion);
}
