package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.exception.SendingMailException;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.service.validators.RegistrationFormValidator;
import com.financial.pyramid.web.form.AuthenticationForm;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:45
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private com.financial.pyramid.service.UserService userService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Validator registrationFormValidator = new RegistrationFormValidator();

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(RedirectAttributes redirectAttributes, ModelMap model, @ModelAttribute("registration") final RegistrationForm registration) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(registration, "registration");
        registrationFormValidator.validate(registration, result);
        if (result.getErrorCount() > 0) {
            model.addAttribute("registration", registration);
            model.put("errors", result.getAllErrors());
            return "/tabs/user/registration-form";
        }
        boolean success = false;
        try {
            success = registrationService.registration(registration);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/pyramid/office";
        } catch (SendingMailException e) {
            e.printStackTrace();
        }
        if (!success) {
            model.addAttribute("registration", registration);
            model.put(AlertType.ERROR.getName(), getMessage("exception.serviceIsNotAvailable"));
            return "tabs/user/registration-form";
        }
        redirectAttributes.addFlashAttribute(AlertType.SUCCESS.getName(), getMessage("alert.registrationIsSuccessful"));
        return "redirect:/pyramid/office";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public String authentication(ModelMap model, @ModelAttribute("authentication") final AuthenticationForm authentication) {
        logger.info("Successfully authenticated. Security context contains: " + SecurityContextHolder.getContext().getAuthentication());
        return "/tabs/office";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PageForm list(ModelMap model, @ModelAttribute("queryForm") final QueryForm queryForm) {
        return new PageForm<User>(userService.findByQuery(queryForm));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("registration") final RegistrationForm registration) {
        if (registration.getId() == null || registration.getId().isEmpty()) {
            registrationService.registration(registration);
        } else {
            userService.update(registration);
        }
        return "redirect:/pyramid/admin/user_settings";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") final Long id) {
        userService.delete(id);
        return "redirect:/pyramid/admin/user_settings";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String forgot(ModelMap model) {
        return "/tabs/forgot";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String restore(ModelMap model, @RequestParam(value = "email") String email) {
//        List<User> users = userService.findByEmail(email.trim());
//        boolean result = false;
//        if (!users.isEmpty()) {
//            User user = users.get(0);
//            String newPassword = userService.createPassword(15);
//            user.setPassword(passwordEncoder.encode(newPassword));
//            userService.saveUser(user);
//            Map map = new HashMap();
//            map.put("username", user.getName());
//            map.put("password", newPassword);
//            emailService.setTemplate("password-restore-template");
//            result = emailService.sendToUser(user, map);
//        }
//        model.addAttribute("result", result);
//        model.addAttribute("email", email);
        return "redirect:/user/forgot";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
