package com.financial.pyramid.web;

import com.financial.pyramid.web.form.Authentication;
import com.financial.pyramid.web.form.Registration;
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

/**
 * User: Danil
 * Date: 30.05.13
 * Time: 21:12
 */

@Controller
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("authentication", new Authentication());
        return "login";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public String authentication(ModelMap model, @ModelAttribute("authentication") final Authentication authentication) {
        try {
            org.springframework.security.core.Authentication request =
                    new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getPassword());
            org.springframework.security.core.Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("registration", new Registration());
        return "registration";
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
