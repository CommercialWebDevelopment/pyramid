package com.financial.pyramid.utils;

import com.financial.pyramid.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: dbudunov
 * Date: 29.08.13
 * Time: 15:19
 */
public class Session {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (User) authentication.getDetails();
        }
        return null;
    }
}
