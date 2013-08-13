package com.financial.pyramid.service.exception;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:32
 */
public class PayPalException extends RuntimeException {

    public PayPalException(Exception e){
        super(e);
    }
}
