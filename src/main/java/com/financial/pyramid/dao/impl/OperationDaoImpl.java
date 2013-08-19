package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.OperationDao;
import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:32
 */
@Repository(value = "operationDao")
public class OperationDaoImpl extends AbstractDaoImpl<Operation,Long> implements OperationDao {
    protected OperationDaoImpl(){
        super(Operation.class);
    }

    public List<Operation> findByQuery(QueryForm form){
        Criteria criteria = getCurrentSession().createCriteria(Operation.class);
        criteria.setCacheable(true)
                .addOrder(form.order.equals("asc") ? Order.asc(form.sort) : Order.desc(form.sort))
                .setFirstResult((form.page - 1) * form.count)
                .setMaxResults(form.count);
        return (List<Operation>) criteria.list();
    }
}
