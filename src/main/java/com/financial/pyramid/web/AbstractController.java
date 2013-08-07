package com.financial.pyramid.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 21:21
 */

@Controller
public class AbstractController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Throwable t) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", t.getMessage());
        return model;
    }
}
