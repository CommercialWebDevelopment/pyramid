package com.financial.pyramid.web;

import com.financial.pyramid.domain.Contact;
import com.financial.pyramid.service.ContactService;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.form.FeedbackForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 14:03
 */
@Controller
@RequestMapping("/contacts")
public class ContactsController extends AbstractController {

    @Autowired
    ContactService contactService;

    @Autowired
    EmailService emailService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("contact") Contact contact) {
        contactService.save(contact);
        return "redirect:/pyramid/admin/contact_settings";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(ModelMap model, @PathVariable Long id) {
        contactService.remove(id);
        return "redirect:/pyramid/admin/contact_settings";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String sendFeedback(ModelMap model, @ModelAttribute("feedbackForm") FeedbackForm feedbackForm) {
        String adminEmail = settingsService.getProperty(Setting.FEEDBACK_RECEIVER_EMAIL);
        com.financial.pyramid.domain.User adminUser = userService.findByEmail(adminEmail);
        Object principal = Session.getAuthentication().getPrincipal();
        String currentUserName = feedbackForm.getName();
        String currentUserEmail = feedbackForm.getEmail();
        String feedback = feedbackForm.getFeedback();
        if (!principal.equals("anonymousUser")) {
            com.financial.pyramid.domain.User user = Session.getCurrentUser();
            currentUserName = user.getName();
            currentUserEmail = user.getEmail();
        }
        Map map = new HashMap();
        map.put("name", adminUser.getName());
        map.put("username", currentUserName);
        map.put("usermail", currentUserEmail);
        map.put("feedback", feedback);
        map.put("subject", localizationService.translate("feedbackFromUser"));
        emailService.setTemplate("feedback-template");
        boolean result = emailService.sendEmail(adminUser, map);
        return "redirect:/pyramid/contacts?sent=" + result;
    }
}