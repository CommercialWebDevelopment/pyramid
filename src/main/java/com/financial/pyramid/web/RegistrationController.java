package com.financial.pyramid.web;

import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 20:46
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.POST)
    public String registration(ModelMap model, @ModelAttribute("registration") final RegistrationForm registration) {
        try {
            registrationService.registration(registration);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("registration", new RegistrationForm());
            return "registration";
        }
        return "index";
    }
}
