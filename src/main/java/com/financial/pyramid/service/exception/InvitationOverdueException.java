package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 13.08.13
 * Time: 23:33
 */
public class InvitationOverdueException extends RuntimeException {

    public InvitationOverdueException() {
        super("Confirm Overdue");
    }
}