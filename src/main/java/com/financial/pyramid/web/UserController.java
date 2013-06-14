package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUser(Model model, @PathVariable Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(ModelMap model, @ModelAttribute("registration") final RegistrationForm registration) {
        model.addAttribute("registration", registration);
//        try {
//            registrationService.registration(registration);
//        } catch (UserAlreadyExistsException e) {
//            model.addAttribute("registration", new RegistrationForm());
//            return "registration";
//        }
        return "index";
    }
}
