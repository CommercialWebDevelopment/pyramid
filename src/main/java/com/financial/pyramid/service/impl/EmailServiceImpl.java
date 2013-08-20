package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
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

    @Override
    public boolean sendToUser(String email, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);
            helper.setText(text, true);
            this.mailSender.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean sendToUser(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(user.getEmail());

            Map model = new HashMap();
            model.put("userName", user.getName());
            model.put("uuid", user.getGlobalId());
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "email-template.vm", "UTF-8", model);
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
}
