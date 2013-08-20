package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.EmailService;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
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

    private String template;

    @Override
    public boolean sendToUser(User user, Map model) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(user.getEmail());
            if (this.template == null) {
                throw new RuntimeException("Template is not set");
            }
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, this.template, "UTF-8", model);
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

    public void setTemplate(String template) {
        Locale locale = LocaleContextHolder.getLocale();
        this.template = template + "-" + locale.toString() + ".vm";
    }

    public String getTemplate() {
        return template;
    }
}
