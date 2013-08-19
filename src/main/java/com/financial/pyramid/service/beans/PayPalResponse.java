package com.financial.pyramid.service.beans;

import java.util.List;

/**
 * User: dbudunov
 * Date: 15.08.13
 * Time: 21:20
 */
public class PayPalResponse {
    public ResponseEnvelope responseEnvelope;
    public String payKey;
    public String paymentExecStatus;
    public List<Error> error;

    public class ResponseEnvelope {
        public String timestamp;
        public String ack;
        public String correlationId;
        public String build;
    }

    public class Error {
        public String message;
    }
}
