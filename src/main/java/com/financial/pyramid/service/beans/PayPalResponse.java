package com.financial.pyramid.service.beans;

/**
 * User: dbudunov
 * Date: 15.08.13
 * Time: 21:20
 */
public class PayPalResponse {
    public ResponseEnvelope responseEnvelope;
    public String payKey;
    public String paymentExecStatus;

    private class ResponseEnvelope {
        public String timestamp;
        public String ack;
        public String correlationId;
        public String build;
    }
}
