package com.financial.pyramid.domain;

/**
 * User: Danil
 * Date: 16.06.13
 * Time: 15:28
 */
public enum Role {
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    UNVERIFIED_USER("UNVERIFIED_USER");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
