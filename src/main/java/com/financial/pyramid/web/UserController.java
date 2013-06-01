package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUser(Model model, @PathVariable Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(Model model, @ModelAttribute("registration") final Registration registration) {
//        StandardPasswordEncoder encoder = new StandardPasswordEncoder(SALT);
//        String result = encoder.encode(registration.getPassword());
//        assertTrue(encoder.matches("myPassword", result));

//        User existing = userService.findById(user.getId());
//        if (existing != null) {
//            model.addAttribute("status", "exist");
//            return "index";
//        }
//        userService.saveUser(user);
//        model.addAttribute("created", "success");
        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(Model model, User user) {
        userService.saveUser(user);
        model.addAttribute("saved", "success");
        return "update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(Model model, @PathVariable Long id) {
        userService.deleteUser(id);
        model.addAttribute("deleted", "success");
        return "index";
    }
}
