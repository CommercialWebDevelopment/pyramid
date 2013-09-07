package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 05.09.13
 * Time: 19:07
 */
public class UserRegistrationException extends RuntimeException {

    private String code;

    public UserRegistrationException(String code) {
        this.code = "exception."+code;
    }

    public String getCode() {
        return code;
    }
}
