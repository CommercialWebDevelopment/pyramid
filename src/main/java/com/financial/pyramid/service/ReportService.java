package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 15.09.13
 * Time: 16:44
 */
public interface ReportService {
    public List<Object[][]> getEarningsReportData(Date dateFrom, Date dateTo, User user);
}
