package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;

/**
 * User: Danil
 * Date: 06.06.13
 * Time: 20:37
 */
public interface EmailService {
    public boolean sendToUser(User user);
    public boolean sendToUser(String email, String text);
}
