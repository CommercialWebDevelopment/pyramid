package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 15.06.13
 * Time: 21:00
 */
public class UserConfirmOverdueException extends RuntimeException {

    public UserConfirmOverdueException() {
        super("Confirm Overdue");
    }
}
