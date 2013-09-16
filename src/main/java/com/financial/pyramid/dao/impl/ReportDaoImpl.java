package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.ReportDao;
import com.financial.pyramid.domain.Account;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 15.09.13
 * Time: 16:51
 */
@Repository(value = "reportDao")
public class ReportDaoImpl extends AbstractDaoImpl<Account, Long> implements ReportDao {

    protected ReportDaoImpl() {
        super(Account.class);
    }

    @Override
    public List<Double> getEarningsData(Date dateFrom, Date dateTo, Long accountId) {
        String queryStr = "select sum(e.count) from Transaction e where e.type='IN' and e.account.id=:accountId " +
                "and e.created between :dateFrom and :dateTo group by MONTH(e.created) order by MONTH(e.created)";
        Query query = super.createQuery(queryStr);
        query.setParameter("accountId", accountId);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        List objects = query.list();
        List<Double> result = new ArrayList<Double>();
        for (Object object : objects) {
            result.add(Double.parseDouble(object.toString()));
        }
        return result;
    }
}
