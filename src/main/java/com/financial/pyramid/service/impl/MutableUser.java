package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.MutableUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 0:08
 */
public class MutableUser implements MutableUserDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String password;
    private final UserDetails delegate;

    public MutableUser(UserDetails user) {
        this.delegate = user;
        this.password = user.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    public String getUsername() {
        return delegate.getUsername();
    }

    public boolean isAccountNonExpired() {
        return delegate.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return delegate.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return delegate.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return delegate.isEnabled();
    }
}

