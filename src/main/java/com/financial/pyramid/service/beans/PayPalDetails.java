package com.financial.pyramid.service.beans;

import java.util.List;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:48
 */
public class PayPalDetails {
    public String actionType;
    public String currencyCode;
    public String feesPayer;
    public String memo;
    public String preApprovalKey;
    public List<Receiver> receiverList;
    public String returnUrl;
    public String cancelUrl;
    public String senderEmail;
    public RequestEnvelope requestEnvelope;
    public boolean reverseAllParallelPaymentsOnError = true;
    public String amount;
    public String email;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFeesPayer() {
        return feesPayer;
    }

    public void setFeesPayer(String feesPayer) {
        this.feesPayer = feesPayer;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPreApprovalKey() {
        return preApprovalKey;
    }

    public void setPreApprovalKey(String preApprovalKey) {
        this.preApprovalKey = preApprovalKey;
    }

    public List<Receiver> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<Receiver> receiverList) {
        this.receiverList = receiverList;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public RequestEnvelope getRequestEnvelope() {
        return requestEnvelope;
    }

    public void setRequestEnvelope(RequestEnvelope requestEnvelope) {
        this.requestEnvelope = requestEnvelope;
    }

    public boolean isReverseAllParallelPaymentsOnError() {
        return reverseAllParallelPaymentsOnError;
    }

    public void setReverseAllParallelPaymentsOnError(boolean reverseAllParallelPaymentsOnError) {
        this.reverseAllParallelPaymentsOnError = reverseAllParallelPaymentsOnError;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
