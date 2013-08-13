package com.financial.pyramid.service;

import com.financial.pyramid.service.beans.PayUDetails;
import com.financial.pyramid.service.exception.PayUException;

/**
 * User: dbudunov
 * Date: 09.08.13
 * Time: 22:06
 */
public interface PayUService {

    public String processPayment(PayUDetails details) throws PayUException;

    public String rejectPayment(PayUDetails details) throws PayUException;

}
