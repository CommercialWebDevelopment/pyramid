package com.financial.pyramid.web;

import com.financial.pyramid.service.LocalizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 21:21
 */

@Controller
public class AbstractController {

    protected final Logger logger = Logger.getLogger(getClass());

    @Autowired
    protected LocalizationService localizationService;

    protected static enum  AlertType {
        ERROR("alert_error"),
        SUCCESS("alert_success");
        private String name;

        private AlertType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Throwable t) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", t.getMessage());
        return model;
    }
}
