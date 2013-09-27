package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.ReportDao;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.ReportService;
import com.financial.pyramid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 15.09.13
 * Time: 16:45
 */
@Service("reportService")
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Object[][]> getEarningsReportData(Date dateFrom, Date dateTo, User user) {
        List<Object[][]> result = new ArrayList<Object[][]>();
        User u = userService.findById(user.getId());
        long accountId = u.getAccount().getId();
        List<Double> earnings = reportDao.getEarningsData(dateFrom, dateTo, accountId);
        if (!earnings.isEmpty()) {
            for (int i = 1; i <= earnings.size(); i++) {
                BigDecimal sum = new BigDecimal(earnings.get(i - 1)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                result.add(new Object[][]{{i}, {sum}});
            }
        } else {
            result.add(new Object[][]{{0},{0}});
        }
        return result;
    }
}
