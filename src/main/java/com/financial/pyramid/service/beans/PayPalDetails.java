package com.financial.pyramid.service.beans;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:48
 */
public class PayPalDetails {
    String payerID;
    String payeeID;
    String amount;

    public String getPayerID() {
        return payerID;
    }

    public void setPayerID(String payerID) {
        this.payerID = payerID;
    }

    public String getPayeeID() {
        return payeeID;
    }

    public void setPayeeID(String payeeID) {
        this.payeeID = payeeID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
