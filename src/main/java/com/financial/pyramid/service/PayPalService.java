package com.financial.pyramid.service;

import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.service.exception.PayPalException;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:46
 */
public interface PayPalService {
    public String processPayment(PayPalDetails details) throws PayPalException;

    public boolean processTransfer(PayPalDetails details) throws PayPalException;

    public boolean isTransactionCompleted(String transactionId) throws PayPalException;

    public boolean isTransactionCompleted(String uid, String type) throws PayPalException;

    public void updatePayPalDetails(PayPalDetails details);
}
