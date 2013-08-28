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
    @RequestMapping(value = "/earnings/{period}", method = RequestMethod.GET)
    public String load(ModelMap model, @PathVariable Long period) {
        EarningsReport report = new EarningsReport();
        double a[][] = new double[][]{};
        if (period == 3) {
            report.label = "Japan";
            a = new double[][]{{1, -0.1}, {2, 2.9}, {3, 0.2}, {4, 0.3}, {5, 1.4}, {6, 2.7}, {7, 1.9}};
        } else if (period == 6) {
            report.label = "England";
            a = new double[][]{{1, -0.1}, {2, 2.9}, {3, 0.2}};
        } else if (period == 12) {
            report.label = "France";
            a = new double[][]{{1, 1.1}, {2, 51.9}, {3, 15}};
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
