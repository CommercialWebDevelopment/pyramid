package com.financial.pyramid.web;

import com.google.gson.Gson;
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
            a = new double[][]{{1, 100}, {2, 200}, {3, 150}};
        } else if (period == 6) {
            a = new double[][]{{1, 50}, {2, 100}, {3, 70}, {4, 120}, {5, 200}, {6, 500}};
        } else if (period == 12) {
            a = new double[][]{{1, 100}, {2, 50}, {3, 15}, {4, 100}, {5, 1}, {6, 20}, {7, 1000}, {8, 900}, {9, 500}, {8, 50}, {9, 79}, {10, 15}, {11, 200}, {12, 300}};
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
