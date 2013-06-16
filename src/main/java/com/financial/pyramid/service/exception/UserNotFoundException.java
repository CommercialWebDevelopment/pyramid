package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 15.06.13
 * Time: 20:49
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User not found");
    }
}
