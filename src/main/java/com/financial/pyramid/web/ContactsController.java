package com.financial.pyramid.web;

import com.financial.pyramid.domain.Contact;
import com.financial.pyramid.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 14:03
 */
@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    ContactService contactService;

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
}