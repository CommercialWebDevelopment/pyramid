package com.financial.pyramid.web;

import com.google.gson.Gson;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: dbudunov
 * Date: 12.08.13
 * Time: 20:41
 */
@Controller
@RequestMapping("/reports")
public class ReportsController extends AbstractController {

    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','USER')")
    @RequestMapping(value = "/earnings/{period}", method = RequestMethod.GET)
    public String load(ModelMap model, @PathVariable Long period) {
        EarningsReport report = new EarningsReport();
        double a[][] = new double[][]{};
        if (period == 3) {
            report.label = "Japan";
            a = new double[][]{{1999, -0.1}, {2000, 2.9}, {2001, 0.2}, {2002, 0.3}, {2003, 1.4}, {2004, 2.7}, {2005, 1.9}, {2006, 2.0}, {2007, 2.3}, {2008, -0.7}};
        } else if (period == 6) {
            report.label = "England";
            a = new double[][]{{1999, -0.1}, {2000, 2.9}, {2001, 0.2}};
        } else if (period == 12) {
            report.label = "France";
            a = new double[][]{{1999, 1.1}, {2000, 51.9}, {2001, 15}};
        }
        report.data = a;
        Gson gson = new Gson();
        return gson.toJson(report);
    }

    private class EarningsReport {
        private String label;
        private double data[][];
    }
}
