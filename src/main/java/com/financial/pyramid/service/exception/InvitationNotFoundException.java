package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 13.08.13
 * Time: 23:35
 */

public class InvitationNotFoundException extends RuntimeException {

    public InvitationNotFoundException() {
        super("Confirm Overdue");
    }
}