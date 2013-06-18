package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.exception.UserConfirmOverdueException;
import com.financial.pyramid.service.exception.UserNotFoundException;
import com.financial.pyramid.service.validators.RegistrationFormValidator;
import com.financial.pyramid.web.form.AuthenticationForm;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:45
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private com.financial.pyramid.service.UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Validator registrationFormValidator = new RegistrationFormValidator();

    @RequestMapping(value = "/checkLogin/{login}", method = RequestMethod.GET)
    public @ResponseBody
    String checkLogin(Model model, @PathVariable String login) {
        return String.valueOf(userService.checkLogin(login));
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(ModelMap model, @ModelAttribute("registration") final RegistrationForm registration) {
        model.addAttribute("page-name", "office");
        model.addAttribute("registration", registration);
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(registration, "registration");
        registrationFormValidator.validate(registration, result);
        if(result.getErrorCount() > 0) {
            return "/tabs/office";
        }
        List<User> users = userService.findByLogin(registration.getLogin());
        if(users.size() > 0) {
            return "/tabs/office";
        }
        boolean tr = registrationService.registration(registration);
        if(!tr) return "/tabs/registration-fail";
        return "/tabs/registration-success";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
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

    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"ui"})
    public String confirmed(ModelMap model, @RequestParam(value = "ui") String uuid) {
        model.addAttribute("page-name", "office");
        try {
            userService.confirm(uuid);
        } catch (UserNotFoundException e) {
            return "/tabs/user-not-found";
        } catch (UserConfirmOverdueException e) {
            return "/tabs/confirm-overdue";
        }
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/login";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    PageForm list(ModelMap mode, @ModelAttribute("queryForm") final QueryForm queryForm) {
        return new PageForm<User>(userService.findByQuery(queryForm));
    }
}
