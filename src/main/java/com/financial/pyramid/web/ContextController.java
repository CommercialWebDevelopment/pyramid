package com.financial.pyramid.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: Danil
 * Date: 23.06.13
 * Time: 3:18
 */
@Controller
public class ContextController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/pyramid/home";
    }
}
