package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.OperationDao;
import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:32
 */
@Repository(value = "operationDao")
public class OperationDaoImpl extends AbstractDaoImpl<Operation, Long> implements OperationDao {
    protected OperationDaoImpl() {
        super(Operation.class);
    }

    @Override
    public List<Operation> findByQuery(QueryForm form) {
        Criteria criteria = getCurrentSession().createCriteria(Operation.class);
        criteria.setCacheable(true)
                .addOrder(form.order.equals("asc") ? Order.asc(form.sort) : Order.desc(form.sort))
                .setFirstResult((form.page - 1) * form.count)
                .setMaxResults(form.count);
        return (List<Operation>) criteria.list();
    }

    @Override
    public void updateStatus(String trackingId, boolean status) {
        String queryStr = "update Operation o set o.complete=:status where o.globalId=:globalId";
        Query query = super.createQuery(queryStr)
                .setParameter("status", status)
                .setParameter("globalId", trackingId);
        query.executeUpdate();
    }

    @Override
    public Double getTransferredAmount(Date date, Long userId) {
        double result = 0;
        Date end = new DateTime(date).plusDays(1).toDate();
        String queryStr = "select sum(o.amount) from Operation o" +
                " where o.date > :dayStart and o.date < :dayEnd" +
                " and o.success = true" +
                " and o.complete = true" +
                " and o.userId = :userId";
        Query query = super.createQuery(queryStr)
                .setParameter("dayStart", date)
                .setParameter("dayEnd", end)
                .setParameter("userId", userId);

        List list = query.list();
        if (!list.isEmpty()) {
            Object amount = (Object) list.get(0);
            result = Double.valueOf(amount != null ? amount.toString() : "0");
        }
        return result;
    }
}
