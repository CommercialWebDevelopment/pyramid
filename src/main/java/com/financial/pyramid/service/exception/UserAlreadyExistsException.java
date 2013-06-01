package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 21:18
 */
public class UserAlreadyExistsException extends IllegalArgumentException {

    public UserAlreadyExistsException() {
        super("User already exist");
    }
}
