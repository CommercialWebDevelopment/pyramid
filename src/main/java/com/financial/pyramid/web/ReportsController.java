package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.ReportService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.utils.Session;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 12.08.13
 * Time: 20:41
 */
@Controller
@RequestMapping("/reports")
public class ReportsController extends AbstractController {

    @Autowired
    private ReportService reportService;

    @ResponseBody
    @RequestMapping(value = "/earnings/{period}", method = RequestMethod.GET)
    public String load(ModelMap model, @PathVariable Long period) {
        EarningsReport report = new EarningsReport();
        if (Session.getCurrentUser() != null) {
            Long currentDate = new Date().getTime();
            Date dateTo = new Date(currentDate);
            Date dateFrom = null;
            if (period == 3) {
                dateFrom = new DateTime(currentDate).minusMonths(3).toDate();
            } else if (period == 6) {
                dateFrom = new DateTime(currentDate).minusMonths(6).toDate();
            } else if (period == 12) {
                dateFrom = new DateTime(currentDate).minusMonths(12).toDate();
            }
            report.data = reportService.getEarningsReportData(dateFrom, dateTo, Session.getCurrentUser());
        }
        Gson gson = new Gson();
        return gson.toJson(report);
    }

    private class EarningsReport {
        private String label;
        private List<Object[][]> data = new ArrayList<Object[][]>();
    }
}
