package com.financial.pyramid.web;

import com.financial.pyramid.web.form.AuthenticationForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 20:43
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    private final static Logger logger = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/home";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String authentication(ModelMap model, @ModelAttribute("authentication") final AuthenticationForm authentication) {
        model.addAttribute("page-name", "office");
        try {
            org.springframework.security.core.Authentication request =
                    new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getPassword());
            org.springframework.security.core.Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            model.addAttribute("registration", new RegistrationForm());
            return "/tabs/login";
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());
        return "/tabs/office";
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public String expired(ModelMap model) {
        return "session-expired";
    }
}
