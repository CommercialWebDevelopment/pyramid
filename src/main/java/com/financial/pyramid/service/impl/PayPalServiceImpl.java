package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.PayPalService;
import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.service.exception.PayPalException;
import org.springframework.stereotype.Service;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:50
 */
@Service
public class PayPalServiceImpl implements PayPalService {

    @Override
    public String processPayment(PayPalDetails details) throws PayPalException {
        return null;
    }
}
