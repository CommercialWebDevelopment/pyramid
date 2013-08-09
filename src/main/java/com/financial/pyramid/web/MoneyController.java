package com.financial.pyramid.web;

import com.financial.pyramid.service.PayUService;
import com.financial.pyramid.service.PerfectMoneyService;
import com.financial.pyramid.service.beans.PayUDetails;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 21:06
 */
@Controller
@RequestMapping("/money")
public class MoneyController extends AbstractController {

    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Autowired
    private PayUService payUService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView get(ModelMap model){
        return new ModelAndView("balance");
    }

    @ResponseBody
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public String getBalance(ModelMap model,
                             @ModelAttribute("accountID") String accountId,
                             @ModelAttribute("password") String password) {
        return perfectMoneyService.getAccountBalance(accountId, password);
    }

    @ResponseBody
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String pay(ModelMap model,
                             @ModelAttribute("accountID") String accountId,
                             @ModelAttribute("password") String password) {
        PayUDetails details = new PayUDetails();
        details.setMerchant("TEST");
        details.setReference("100500");
        details.setAmount("1234");
        details.setCurrency("RUB");
        details.setDate("2011-10-01 12:12:12");
        return payUService.processPayment(details);
    }


}
