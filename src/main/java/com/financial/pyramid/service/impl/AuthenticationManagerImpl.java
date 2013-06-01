package com.financial.pyramid.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Danil
 * Date: 30.05.13
 * Time: 21:27
 */
@Service("authenticationService")
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static final List<GrantedAuthority> ADMIN_ROLES = new ArrayList<GrantedAuthority>();
    static final List<GrantedAuthority> USER_ROLES = new ArrayList<GrantedAuthority>();
    static {
        ADMIN_ROLES.add(new SimpleGrantedAuthority("ADMIN"));
        USER_ROLES.add(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals(authentication.getCredentials())) {
            Authentication a = new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(), USER_ROLES);
            return a;
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
