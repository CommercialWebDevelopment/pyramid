package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;

import java.util.Map;

/**
 * User: Danil
 * Date: 06.06.13
 * Time: 20:37
 */
public interface EmailService {
    public boolean sendToUser(User user, Map model);

    public void setTemplate(String template);

    public String getTemplate();
}
