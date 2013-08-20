package com.financial.pyramid.service.exception;

/**
 * User: Danil
 * Date: 19.08.13
 * Time: 21:55
 */
public class SendingMailException extends RuntimeException {
    public SendingMailException() {
        super("Error sending mail");
    }
}
