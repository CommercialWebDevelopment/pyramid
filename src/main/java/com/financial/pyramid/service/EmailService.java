package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;

import java.util.Map;

/**
 * User: Danil
 * Date: 06.06.13
 * Time: 20:37
 */
public interface EmailService {

    public boolean sendEmail(User user, Map model);

    public void setTemplate(String template);

    public String getTemplate();

    public boolean sendInvitation(String uuid, String name, String email);

    public boolean sendPassword(String name, String password, String email);

    public boolean checkEmail(String email);
}
