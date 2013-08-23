package com.financial.pyramid.web;

import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.InvitationService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.InvitationNotFoundException;
import com.financial.pyramid.service.exception.InvitationOverdueException;
import com.financial.pyramid.web.form.InvitationForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 22:14
 */
@Controller
@RequestMapping("/invitation")
public class InvitationController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String invitation(RedirectAttributes redirectAttributes, ModelMap model, @ModelAttribute("invitation") final InvitationForm invitationForm) {
        if (!emailService.checkEmail(invitationForm.getEmail())) {
            redirectAttributes.addFlashAttribute(AlertType.ERROR.getName(), getMessage("exception.emailIsNotCorrect", invitationForm.getEmail()));
            return "redirect:/pyramid/office";
        }
        if (userService.findByEmail(invitationForm.getEmail()) != null) {
            redirectAttributes.addFlashAttribute(AlertType.ERROR.getName(), getMessage("exception.userAlreadyExistWithEmail", invitationForm.getEmail()));
            return "redirect:/pyramid/office";
        }
        if (invitationService.findByEmail(invitationForm.getEmail()) != null) {
            redirectAttributes.addFlashAttribute(AlertType.ERROR.getName(), getMessage("exception.userAlreadyHasInvitation", invitationForm.getEmail()));
            return "redirect:/pyramid/office";
        }
        if (!invitationService.sendInvitation(invitationForm)) {
            redirectAttributes.addFlashAttribute(AlertType.ERROR.getName(), getMessage("exception.serviceIsNotAvailable"));
            return "redirect:/pyramid/office";
        }
        redirectAttributes.addFlashAttribute(AlertType.SUCCESS.getName(), getMessage("alert.invitationWasSent"));
        return "redirect:/pyramid/office";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"ui"})
    public String confirm(ModelMap model, @RequestParam(value = "ui") String uuid) {
        try {
            Invitation invitation = invitationService.confirm(uuid);

            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.setInvitationId(invitation.getId());

            model.addAttribute("registration", registrationForm);
            return "/tabs/user/registration-form";
        } catch (InvitationNotFoundException e) {
            return "/tabs/user/private-office";
        } catch (InvitationOverdueException e) {
            return "/tabs/user/private-office";
        }
    }
}
