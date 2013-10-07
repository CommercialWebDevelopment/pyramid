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
    public String notifyUrl;
    public String senderEmail;
    public RequestEnvelope requestEnvelope;
    public boolean reverseAllParallelPaymentsOnError = true;
    public String amount;
    public String receiverEmail;
    public String globalId;
    public String currencySign;
    public int months;

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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
