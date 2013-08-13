package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.PayPalService;
import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.service.exception.PayPalException;
import com.financial.pyramid.utils.HTTPClient;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private boolean isSuccessfulPayment(String paymentTransactionID){
        List<String> response = HTTPClient.sendRequest(getPDTUrl("url","token",paymentTransactionID));
        String responseText = response.toString();
        return responseText.contains("SUCCESS");
    }

    private String getPDTUrl(String payPalUrl, String token, String transactionID) {
        return new StringBuilder()
                .append(payPalUrl)
                .append("?cmd=_notify-synch&tx=")
                .append(transactionID)
                .append("&at=")
                .append(token)
                .toString();
    }
}
