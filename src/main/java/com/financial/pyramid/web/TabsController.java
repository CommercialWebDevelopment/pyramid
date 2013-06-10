package com.financial.pyramid.web;

import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.web.form.AuthenticationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: Danil
 * Date: 05.06.13
 * Time: 23:03
 */
@Controller
@RequestMapping("/")
public class TabsController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        model.addAttribute("authentication", new AuthenticationForm());
        return "index";
    }
}
