package com.financial.pyramid.web;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.beans.AccountDetails;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.service.exception.UserRegistrationException;
import com.financial.pyramid.service.validators.RegistrationFormValidator;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.MD5Encoder;
import com.financial.pyramid.utils.Password;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.form.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    ServletContext servletContext;

    private Validator registrationFormValidator = new RegistrationFormValidator();

    private static String SECRET_KEY = "1234567890qwerty";

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(RedirectAttributes redirectAttributes, ModelMap model, @ModelAttribute("registration") final RegistrationForm registration) {
        try {
            registrationService.registration(registration);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/app/office";
        } catch (UserRegistrationException e) {
            model.put(AlertType.ERROR.getName(), localizationService.translate(e.getCode()));
            return "tabs/user/registration-form";
        }
        redirectAttributes.addFlashAttribute(AlertType.SUCCESS.getName(), localizationService.translate("alert.registrationIsSuccessful"));
        return "redirect:/app/office";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public String authentication(ModelMap model, @ModelAttribute("authentication") final AuthenticationForm authentication) {
        logger.info("Successfully authenticated. Security context contains: " + Session.getAuthentication());
        return "/tabs/office";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PageForm list(ModelMap model, @ModelAttribute("queryForm") final QueryForm queryForm) {
        List<RegistrationForm> result = new ArrayList<RegistrationForm>();
        List<User> users = userService.findByQuery(queryForm);
        for (User user : users) {
            AdminRegistrationForm form = new AdminRegistrationForm();
            AccountDetails accountDetails = userService.getAccountDetails(user);
            form.setId(user.getId());
            form.setName(user.getName());
            form.setSurname(user.getSurname());
            form.setPatronymic(user.getPatronymic());
            form.setDateOfBirth(user.getDateOfBirth().toString());
            form.setPhoneNumber(user.getPhoneNumber());
            form.setEmail(user.getEmail());
            if (user.getPassport() != null) {
                form.setPassportSerial(user.getPassport().getSerial());
                form.setPassportNumber(user.getPassport().getNumber());
                if (user.getPassport().getDate() != null) {
                    form.setPassportDate(user.getPassport().getDate().toString());
                }
                form.setPassportIssuedBy(user.getPassport().getIssuedBy());
                form.setRegisteredAddress(user.getPassport().getRegisteredAddress());
                form.setResidenceAddress(user.getPassport().getResidenceAddress());
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
            form.setDateActivated(dateFormat.format(accountDetails.getDateActivated()));
            form.setDateExpired(dateFormat.format(accountDetails.getDateExpired()));
            result.add(form);
        }
        return new PageForm<RegistrationForm>(result);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("registration") final AdminRegistrationForm registration) {
        userService.update(registration);
        return "redirect:/app/admin/user_settings";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") final Long id) {
        userService.delete(id);
        return "redirect:/app/admin/user_settings";
    }

    @RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
    public String activate(@PathVariable("id") final Long id){
        User user = userService.findById(id);
        userService.activateUserAccount(user, 1);
        return "redirect:/app/admin/user_settings";
    }

    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.GET)
    public String deactivate(@PathVariable("id") final Long id){
        User user = userService.findById(id);
        userService.deactivateUserAccount(user);
        return "redirect:/app/admin/user_settings";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String forgot(ModelMap model) {
        return "/tabs/forgot";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String restore(ModelMap model, @RequestParam(value = "email") String email) {
        User user = userService.findByEmail(email.trim());
        boolean result = false;
        if (user != null) {
            String newPassword = Password.generate();
            String serverUrl = settingsService.getProperty(Setting.APPLICATION_URL);
            String returnUrl = serverUrl + "/user/restore_confirmed?uuid=" + MD5Encoder.encode(email + SECRET_KEY) + "&email=" + email + "&password=" + newPassword;
            Map map = new HashMap();
            map.put("username", user.getName());
            map.put("password", newPassword);
            map.put("returnUrl", returnUrl);
            map.put("subject", localizationService.translate("passwordIsGenerated"));
            emailService.setTemplate("password-restore-template");
            result = emailService.sendEmail(user, map);
        }
        model.addAttribute("result", result);
        model.addAttribute("email", email);
        return "redirect:/user/forgot";
    }

    @RequestMapping(value = "/restore_confirmed", method = RequestMethod.GET)
    public String restoreConfirm(RedirectAttributes redirectAttributes, ModelMap model,
                                 @RequestParam("email") String email,
                                 @RequestParam("uuid") String uuid,
                                 @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        if (user != null) {
            if (uuid.equals(MD5Encoder.encode(email + SECRET_KEY))) {
                user.setPassword(passwordEncoder.encode(password));
                userService.save(user);
                redirectAttributes.addFlashAttribute(AlertType.SUCCESS.getName(), localizationService.translate("passwordRestored"));
            }
        }
        return "redirect:/app/office";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        User current = Session.getCurrentUser();
        User user = userService.findById(current.getId());
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setId(user.getId());
        registrationForm.setName(user.getName());
        registrationForm.setSurname(user.getSurname());
        registrationForm.setPatronymic(user.getPatronymic());
        registrationForm.setDateOfBirth(user.getDateOfBirth().toString());
        registrationForm.setPhoneNumber(user.getPhoneNumber());
        if (user.getPassport() != null) {
            String passportDate = user.getPassport().getDate() != null ? user.getPassport().getDate().toString() : null;
            registrationForm.setPassportSerial(user.getPassport().getSerial());
            registrationForm.setPassportNumber(user.getPassport().getNumber());
            registrationForm.setPassportDate(passportDate);
            registrationForm.setPassportIssuedBy(user.getPassport().getIssuedBy());
            registrationForm.setRegisteredAddress(user.getPassport().getRegisteredAddress());
            registrationForm.setResidenceAddress(user.getPassport().getResidenceAddress());
        }
        model.addAttribute("user", user);
        model.addAttribute("registration", registrationForm);
        return "/tabs/user/user-settings";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public String changePassword(ModelMap model,
                                 @RequestParam("new_password") String newPassword,
                                 @RequestParam("old_password") String oldPassword) {
        User current = Session.getCurrentUser();
        String password = passwordEncoder.encode(newPassword);
        User user = userService.findById(current.getId());
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(password);
            userService.save(user);
            model.addAttribute("changesSaved", true);
        } else {
            model.addAttribute("invalidPassword", true);
        }
        return "redirect:/user/settings";
    }

    @RequestMapping(value = "/change_email", method = RequestMethod.POST)
    public String changeEmail(ModelMap model,
                              @RequestParam("new_email") String email,
                              @RequestParam("new_email_confirm") String emailConfirmed,
                              @RequestParam("password") String password) {
        User current = Session.getCurrentUser();
        User user = userService.findById(current.getId());
        boolean valid = true;
        if (!email.equals(emailConfirmed)) {
            model.addAttribute("invalidEmail", true);
            valid = false;
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("invalidPassword", true);
            valid = false;
        }
        if (valid) {
            String serverUrl = settingsService.getProperty(Setting.APPLICATION_URL);
            String returnUrl = serverUrl + "/user/email_confirmed?uuid=" + MD5Encoder.encode(email + SECRET_KEY) + "&email=" + email;
            emailService.setTemplate("email-changing");
            String oldEmail = current.getEmail();
            current.setEmail(email);
            Map map = new HashMap();
            map.put("name", current.getName());
            map.put("return_url", returnUrl);
            map.put("subject", localizationService.translate("emailNeedsConfirmation"));
            emailService.sendEmail(current, map);
            current.setEmail(oldEmail);
            model.addAttribute("emailConfirmed", true);
        }
        return "redirect:/user/settings";
    }

    @RequestMapping(value = "/confirm_email", method = RequestMethod.GET)
    public String confirmEmail(ModelMap model) {
        User current = Session.getCurrentUser();
        emailService.setTemplate("email-confirmation");
        Map map = new HashMap();
        map.put("name", current.getName());
        map.put("subject", localizationService.translate("emailNeedsConfirmation"));
        emailService.sendEmail(current, map);
        model.addAttribute("emailConfirmed", true);
        return "redirect:/user/settings";
    }

    @RequestMapping(value = "/email_confirmed", method = RequestMethod.GET)
    public String confirmed(ModelMap model, @RequestParam("email") String email, @RequestParam("uuid") String uuid) {
        User current = Session.getCurrentUser();
        if (current != null) {
            User user = userService.findById(current.getId());
            User existingUser = userService.findByEmail(email);
            if (existingUser == null) {
                if (uuid.equals(MD5Encoder.encode(email + SECRET_KEY))) {
                    user.setEmail(email);
                    userService.save(user);
                    model.addAttribute("changesSaved", true);
                }
            } else {
                model.addAttribute("emailAddress", email);
                model.addAttribute("userExists", true);
            }
            return "redirect:/user/settings";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/save_profile", method = RequestMethod.POST)
    public String saveProfile(ModelMap model, @ModelAttribute("user") User user) {
        User existingUser = userService.findById(user.getId());
        existingUser.setSurname(user.getSurname());
        existingUser.setName(user.getName());
        existingUser.setPatronymic(user.getPatronymic());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        userService.save(existingUser);
        model.addAttribute("changesSaved", true);
        return "redirect:/user/settings";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @RequestMapping(value = "/check_date", method = RequestMethod.GET)
    public
    @ResponseBody
    Boolean checkDate(@RequestParam("date") final String date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, LocaleContextHolder.getLocale());
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @RequestMapping(value = "/contract_offer", method = RequestMethod.GET)
    public String getContractOffer(ModelMap model) {
        return "contract-offer/contract-offer-" + LocaleContextHolder.getLocale().getLanguage();
    }

    @RequestMapping(value = "/contract_offer_pdf", method = RequestMethod.GET)
    public void getContractOfferPDF(HttpServletResponse response) {
        try {
            InputStream is = servletContext.getResourceAsStream("resources/pdf/contract-offer-ru.pdf");
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=contract-offer.pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            IOUtils.copy(is, os);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
