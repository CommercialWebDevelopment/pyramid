package com.financial.pyramid.dao;

import com.financial.pyramid.domain.Account;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 15.09.13
 * Time: 16:47
 */
public interface ReportDao extends AbstractDao<Account, Long> {
    public List<Double> getEarningsData(Date dateFrom, Date dateTo, Long accountId);
}
