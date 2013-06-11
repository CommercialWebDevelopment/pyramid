package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.EmailService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void send() {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("Danilpostoffice@yandex.ru");

            Map model = new HashMap();
            model.put("userName", "Имя пользователя");
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "email-template.vm", "UTF-8", model);
            helper.setText(text, true);
            this.mailSender.send(message);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
