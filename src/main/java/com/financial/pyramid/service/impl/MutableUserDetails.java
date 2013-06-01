package com.financial.pyramid.service.impl;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 0:10
 */
public interface MutableUserDetails extends UserDetails {

    void setPassword(String password);

}