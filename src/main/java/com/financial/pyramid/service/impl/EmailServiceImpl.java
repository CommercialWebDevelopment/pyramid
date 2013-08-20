package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.validators.EmailValidator;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Danil
 * Date: 06.06.13
 * Time: 20:37
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final static Logger logger = Logger.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    private EmailValidator emailValidator = new EmailValidator();

    @Override
    public boolean sendInvitation(String uuid, String name, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);

            Map<String, Object> model = new HashMap<String, Object>(5);
            model.put("name", name);
            model.put("uuid", uuid);
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "invitation-template.vm", "UTF-8", model);
            helper.setText(text, true);
            this.mailSender.send(message);
        } catch (MailException ex) {
            logger.error(ex.getMessage());
            return false;
        } catch (MessagingException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendPassword(String name, String password, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);

            Map<String, Object> model = new HashMap<String, Object>(5);
            model.put("name", name);
            model.put("password", password);
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "password-template.vm", "UTF-8", model);
            helper.setText(text, true);
            this.mailSender.send(message);
        } catch (MailException ex) {
            logger.error(ex.getMessage());
            return false;
        } catch (MessagingException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean checkEmail(String email) {
        if (!emailValidator.validate(email)) {
            logger.warn("Email " + email + " is not valid");
            return false;
        }
        return true;
    }
}
