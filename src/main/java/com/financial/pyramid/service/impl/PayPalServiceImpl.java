package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.paypal.PayPalPropeties;
import com.financial.pyramid.service.ApplicationConfigurationService;
import com.financial.pyramid.service.OperationsLoggingService;
import com.financial.pyramid.service.PayPalService;
import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.service.beans.PayPalResponse;
import com.financial.pyramid.service.beans.Receiver;
import com.financial.pyramid.service.beans.RequestEnvelope;
import com.financial.pyramid.service.exception.PayPalException;
import com.financial.pyramid.utils.HTTPClient;
import com.google.gdata.util.common.base.Pair;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 13.08.13
 * Time: 19:50
 */
@Service
public class PayPalServiceImpl implements PayPalService {

    @Autowired
    ApplicationConfigurationService configurationService;

    @Autowired
    OperationsLoggingService loggingService;

    @Override
    public String processPayment(PayPalDetails payPalDetails) throws PayPalException {
        updatePayPalDetails(payPalDetails);

        List<Receiver> receivers = new ArrayList<Receiver>();
        Receiver receiver = new Receiver();
        receiver.amount = payPalDetails.amount;
        receiver.email = payPalDetails.email;
        receivers.add(receiver);
        payPalDetails.receiverList = receivers;

        List<String> response = HTTPClient.sendRequestWithHeaders(getPaymentUrl(payPalDetails), getHeaders("JSON"), RequestMethod.GET.name());
        String result = isSuccessfulPayment(response) ? "Success" : "Failed";

        if (response.size() == 0){
            throw new PayPalException("Response from PayPal is empty!");
        }

        Operation operation = new Operation();
        operation.setMemo(payPalDetails.memo);
        operation.setType(payPalDetails.actionType);
        operation.setPayer(payPalDetails.senderEmail);
        operation.setDate(new Date(System.currentTimeMillis()));
        operation.setPayee(payPalDetails.receiverList.get(0).email);
        operation.setAmount(Double.valueOf(payPalDetails.receiverList.get(0).amount));
        operation.setSuccess(isSuccessfulPayment(response));
        operation.setResult(result);
        loggingService.save(operation);

        PayPalResponse payPalResponse = new Gson().fromJson(response.toString(), PayPalResponse.class);
        return PayPalPropeties.PAY_PAL_PAYMENT_URL + "?cmd=_ap-payment&paykey=" + payPalResponse.payKey;
    }

    @Override
    public String processTransfer(PayPalDetails details) throws PayPalException {
        updatePayPalDetails(details);

        List<Receiver> receivers = new ArrayList<Receiver>();
        Receiver receiver = new Receiver();
        receiver.amount = details.amount;
        receiver.email = details.email;
        receivers.add(receiver);
        details.receiverList = receivers;

        List<String> response = HTTPClient.sendRequestWithHeaders(getTransferUrl(details), getHeaders("NV"), RequestMethod.GET.name());
        String result = isSuccessfulPayment(response) ? "Success" : "Failed";
        Operation operation = new Operation();
        operation.setMemo(details.memo);
        operation.setType(details.actionType);
        operation.setPayer(details.senderEmail);
        operation.setDate(new Date(System.currentTimeMillis()));
        operation.setPayee(details.receiverList.get(0).email);
        operation.setAmount(Double.valueOf(details.receiverList.get(0).amount));
        operation.setSuccess(isSuccessfulPayment(response));
        operation.setResult(result);
        loggingService.save(operation);
        return result;
    }

    private List<Pair<String, String>> getHeaders(String dataFormat) {
        List<Pair<String, String>> headers = new ArrayList<Pair<String, String>>();
        String securityUserId = configurationService.getParameter("pay_pal_api_username");
        String securityPassword = configurationService.getParameter("pay_pal_api_password");
        String securitySignature = configurationService.getParameter("pay_pal_api_signature");
        String applicationId = configurationService.getParameter("pay_pal_application_id");
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-USERID", securityUserId));
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-PASSWORD", securityPassword));
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-SIGNATURE", securitySignature));
        headers.add(new Pair<String, String>("X-PAYPAL-REQUEST-DATA-FORMAT", dataFormat));
        headers.add(new Pair<String, String>("X-PAYPAL-RESPONSE-DATA-FORMAT", dataFormat));
        headers.add(new Pair<String, String>("X-PAYPAL-APPLICATION-ID", applicationId));
        return headers;
    }

    private String getTransferUrl(PayPalDetails details) {
        String errorLanguage = PayPalPropeties.PAY_PAL_DEFAULT_ERROR_LANGUAGE;
        if (details.requestEnvelope != null && details.requestEnvelope.errorLanguage != null) {
            errorLanguage = details.requestEnvelope.errorLanguage;
        }

        return PayPalPropeties.PAY_PAL_ADAPTIVE_PAYMENT_URL
                + "?actionType=" + PayPalPropeties.PAY_PAL_ACTION_TYPE
                + "&cancelUrl=" + PayPalPropeties.PAY_PAL_CANCEL_URL
                + "&currencyCode=" + PayPalPropeties.PAY_PAL_CURRENCY
                + "&feesPayer=" + PayPalPropeties.PAY_PAL_FEES_PAYER
                + "&memo=" + details.memo
                + "&preapprovalKey=" + details.preApprovalKey
                + "&receiverList.receiver(0).amount=" + details.receiverList.get(0).amount
                + "&receiverList.receiver(0).email=" + details.receiverList.get(0).email
                + "&receiverList.receiver(0).primary=" + details.receiverList.get(0).primary
                + "&requestEnvelope.errorLanguage=" + errorLanguage
                + "&returnUrl=" + PayPalPropeties.PAY_PAL_RETURN_URL
                + "&reverseAllParallelPaymentsOnError=" + details.reverseAllParallelPaymentsOnError
                + "&senderEmail=" + details.senderEmail;
    }

    private String getPaymentUrl(PayPalDetails details) {
        String errorLanguage = PayPalPropeties.PAY_PAL_DEFAULT_ERROR_LANGUAGE;
        if (details.requestEnvelope != null && details.requestEnvelope.errorLanguage != null) {
            errorLanguage = details.requestEnvelope.errorLanguage;
        }

        return PayPalPropeties.PAY_PAL_ADAPTIVE_PAYMENT_URL
                + "?actionType=" + PayPalPropeties.PAY_PAL_ACTION_TYPE
                + "&cancelUrl=" + PayPalPropeties.PAY_PAL_CANCEL_URL
                + "&currencyCode=" + PayPalPropeties.PAY_PAL_CURRENCY
                + "&receiverList.receiver(0).amount=" + details.receiverList.get(0).amount
                + "&receiverList.receiver(0).email=" + details.receiverList.get(0).email
                + "&requestEnvelope.errorLanguage=" + errorLanguage
                + "&requestEnvelope.detailLevel=ReturnAll"
                + "&returnUrl=" + PayPalPropeties.PAY_PAL_RETURN_URL;
    }

    public void updatePayPalDetails(PayPalDetails details) {
        details.actionType = details.actionType == null ? PayPalPropeties.PAY_PAL_ACTION_TYPE : details.actionType;
        details.returnUrl = details.returnUrl == null ? PayPalPropeties.PAY_PAL_RETURN_URL : details.returnUrl;
        details.cancelUrl = details.cancelUrl == null ? PayPalPropeties.PAY_PAL_CANCEL_URL : details.cancelUrl;
        details.currencyCode = details.currencyCode == null ? PayPalPropeties.PAY_PAL_CURRENCY : details.currencyCode;
        details.feesPayer = details.feesPayer == null ? PayPalPropeties.PAY_PAL_FEES_PAYER : details.feesPayer;
    }

    private boolean isSuccessfulPayment(List<String> response) {
        return response.toString().contains("SUCCESS");
    }

    /*
    private boolean isSuccessfulPayment(String paymentTransactionID) {
        List<String> response = HTTPClient.sendRequest(getPDTUrl("url", "token", paymentTransactionID));
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
    */
}
