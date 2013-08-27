package com.financial.pyramid.web;

import com.financial.pyramid.service.*;
import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.settings.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    LocalizationService localizationService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment(ModelMap model) {
        PayPalDetails details = new PayPalDetails();
        payPalService.updatePayPalDetails(details);
        String applicationURL = settingsService.getProperty(Setting.APPLICATION_URL);
        String officePrice = settingsService.getProperty(Setting.OFFICE_PRICE);
        details.receiverEmail = configurationService.getParameter(Setting.PAY_PAL_LOGIN);
        details.cancelUrl = applicationURL + "/paypal/payment";
        details.returnUrl = applicationURL + "/pyramid/office";
        details.amount = officePrice;
        model.addAttribute("payPalDetails", details);
        return "tabs/user/payment";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(ModelMap model, @ModelAttribute("payPalDetails") PayPalDetails details) {
        String officePrice = settingsService.getProperty(Setting.OFFICE_PRICE);
        if (!officePrice.equals(details.amount)){
            details.amount = officePrice;
        }
        details.memo = localizationService.translate("paymentOffice");
        String redirectURL = payPalService.processPayment(details);
        return "redirect:" + redirectURL;
    }

    @RequestMapping(value = "/transferMoney", method = RequestMethod.GET)
    public String transferMoney(ModelMap model){
        PayPalDetails details = new PayPalDetails();
        payPalService.updatePayPalDetails(details);
        String maxAllowedAmount = settingsService.getProperty(Setting.MAX_ALLOWED_AMOUNT);
        String applicationURL = settingsService.getProperty(Setting.APPLICATION_URL);
        details.senderEmail = configurationService.getParameter(Setting.PAY_PAL_LOGIN);
        details.amount = maxAllowedAmount;
        details.cancelUrl = applicationURL + "/paypal/transferMoney";
        details.returnUrl = applicationURL + "/pyramid/office";
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = (User) user;
        com.financial.pyramid.domain.User systemUser = userService.findByEmail(currentUser.getUsername());
        details.receiverEmail = systemUser.getEmail();
        model.addAttribute("payPalDetails", details);
        model.addAttribute("maxAllowedAmount", maxAllowedAmount);
        return "tabs/user/take-money";
    }

    @RequestMapping(value = "/processTransfer", method = RequestMethod.POST)
    public String processTransfer(ModelMap model, @ModelAttribute("payPalDetails") PayPalDetails details) {
        String maxAllowedAmount = settingsService.getProperty(Setting.MAX_ALLOWED_AMOUNT);
        if (!maxAllowedAmount.equals(details.amount)){
            details.amount = maxAllowedAmount;
        }
        details.memo = localizationService.translate("moneyTransfer");
        payPalService.processTransfer(details);
        return "redirect:" + details.returnUrl;
    }
}
