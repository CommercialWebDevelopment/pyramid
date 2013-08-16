package com.financial.pyramid.web;

import com.financial.pyramid.service.ApplicationConfigurationService;
import com.financial.pyramid.service.PayPalService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.beans.PayPalDetails;
import org.hibernate.context.spi.CurrentSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dbudunov
 * Date: 14.08.13
 * Time: 19:53
 */
@Controller
@RequestMapping("/paypal")
public class PayPalController extends AbstractController {

    @Autowired
    PayPalService payPalService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    ApplicationConfigurationService configurationService;

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment(ModelMap model) {
        PayPalDetails details = new PayPalDetails();
        payPalService.updatePayPalDetails(details);
        String applicationURL = settingsService.getProperty("applicationURL");
        String officePrice = settingsService.getProperty("officePrice");
        details.senderEmail = configurationService.getParameter("PAY_PAL_LOGIN");
        details.cancelUrl = applicationURL + "/paypal/payment";
        details.returnUrl = applicationURL + "/tabs/user/private-office";
        details.amount = officePrice;
        model.addAttribute("payPalDetails", details);
        return "tabs/user/payment";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(ModelMap model, @ModelAttribute("payPalDetails") PayPalDetails details) {
        String redirectURL = payPalService.processPayment(details);
        return "redirect:" + redirectURL;
    }
}
