package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.paypal.PayPalPropeties;
import com.financial.pyramid.service.ApplicationConfigurationService;
import com.financial.pyramid.service.OperationsService;
import com.financial.pyramid.service.PayPalService;
import com.financial.pyramid.service.beans.PayPalDetails;
import com.financial.pyramid.service.beans.PayPalResponse;
import com.financial.pyramid.service.beans.Receiver;
import com.financial.pyramid.service.exception.PayPalException;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.HTTPClient;
import com.financial.pyramid.utils.MD5Encoder;
import com.financial.pyramid.utils.Session;
import com.google.gdata.util.common.base.Pair;
import com.google.gson.Gson;
import org.joda.time.DateTime;
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
    OperationsService operationsService;

    private static String PAY_PAL_EMPTY_RESPONSE = "Response from PayPal is empty!";

    @Override
    public String processPayment(PayPalDetails payPalDetails) throws PayPalException {
        PayPalResponse payPalResponse = processPayPalRequest(payPalDetails, true);
        return PayPalPropeties.PAY_PAL_PAYMENT_URL + "?cmd=_ap-payment&paykey=" + payPalResponse.payKey;
    }

    @Override
    public boolean processTransfer(PayPalDetails details) throws PayPalException {
        PayPalResponse payPalResponse = processPayPalRequest(details, false);
        return isTransactionCompleted(payPalResponse.payKey, PayPalPropeties.PAY_PAL_PAY_KEY);
    }

    @Override
    public boolean isTransactionCompleted(String transactionId) throws PayPalException {
        boolean result = false;
        String token = configurationService.getParameter(Setting.PAY_PAL_API_TOKEN);
        String url = PayPalPropeties.PAY_PAL_PAYMENT_URL + "?cmd=_notify-synch&tx=" + transactionId + "&at=" + token;
        try {
            List<String> response = HTTPClient.sendRequest(url);
            result = response.toString().contains("COMPLETED") && response.toString().contains("SUCCESS");
        } catch (Exception e) {
            throw new PayPalException(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean isTransactionCompleted(String uid, String type) throws PayPalException {
        PayPalResponse payPalResponse;
        try {
            String url = getPaymentDetailsUrl(uid, type);
            List<String> response = HTTPClient.sendRequestWithHeaders(url, getHeaders(), RequestMethod.GET.name());
            payPalResponse = new Gson().fromJson(response.get(0), PayPalResponse.class);
        } catch (Exception e) {
            throw new PayPalException(e.getMessage());
        }
        return payPalResponse.status != null && payPalResponse.status.equals("COMPLETED");
    }

    private PayPalResponse processPayPalRequest(PayPalDetails payPalDetails, boolean isPurchasePayment) {
        PayPalResponse payPalResponse;
        try {
            updatePayPalDetails(payPalDetails);
            String url = isPurchasePayment ? getPaymentUrl(payPalDetails) : getTransferUrl(payPalDetails);
            List<String> response = HTTPClient.sendRequestWithHeaders(url, getHeaders(), RequestMethod.GET.name());

            String result = isSuccessfulPayment(response) ? "Success" : "Failed";

            Operation operation = new Operation();
            operation.setMemo(payPalDetails.memo);
            operation.setType(payPalDetails.actionType);
            operation.setPayer(payPalDetails.senderEmail);
            operation.setGlobalId(payPalDetails.globalId);
            operation.setDate(new Date(System.currentTimeMillis()));
            operation.setPayee(payPalDetails.receiverList.get(0).email);
            operation.setAmount(Double.valueOf(payPalDetails.receiverList.get(0).amount));
            operation.setSuccess(isSuccessfulPayment(response));
            operation.setResult(result);

            User user = Session.getCurrentUser();
            operation.setUserId(user.getId());

            payPalResponse = new Gson().fromJson(response.get(0), PayPalResponse.class);
            String errorString = "";
            if (response.isEmpty()) {
                errorString = PAY_PAL_EMPTY_RESPONSE;
            }

            if (payPalResponse.error != null && payPalResponse.error.size() > 0) {
                for (int i = 0; i < payPalResponse.error.size(); i++) {
                    errorString += payPalResponse.error.get(i).message;
                }
            }
            if (!errorString.isEmpty()) {
                operation.setError(errorString);
            }

            operationsService.save(operation);
        } catch (Exception e) {
            throw new PayPalException(e.getMessage());
        }
        return payPalResponse;
    }

    private List<Pair<String, String>> getHeaders() {
        List<Pair<String, String>> headers = new ArrayList<Pair<String, String>>();
        String securityUserId = configurationService.getParameter(Setting.PAY_PAL_API_USERNAME);
        String securityPassword = configurationService.getParameter(Setting.PAY_PAL_API_PASSWORD);
        String securitySignature = configurationService.getParameter(Setting.PAY_PAL_API_SIGNATURE);
        String applicationId = configurationService.getParameter(Setting.PAY_PAL_API_APPLICATION_ID);
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-USERID", securityUserId));
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-PASSWORD", securityPassword));
        headers.add(new Pair<String, String>("X-PAYPAL-SECURITY-SIGNATURE", securitySignature));
        headers.add(new Pair<String, String>("X-PAYPAL-REQUEST-DATA-FORMAT", "NV"));
        headers.add(new Pair<String, String>("X-PAYPAL-RESPONSE-DATA-FORMAT", "JSON"));
        headers.add(new Pair<String, String>("X-PAYPAL-APPLICATION-ID", applicationId));
        return headers;
    }

    private String getTransferUrl(PayPalDetails details) {
        return PayPalPropeties.PAY_PAL_ADAPTIVE_PAYMENT_URL
                + "?actionType=" + PayPalPropeties.PAY_PAL_ACTION_TYPE
                + "&cancelUrl=" + details.cancelUrl
                + "&currencyCode=" + PayPalPropeties.PAY_PAL_CURRENCY
                + "&receiverList.receiver(0).amount=" + details.receiverList.get(0).amount
                + "&receiverList.receiver(0).email=" + details.receiverList.get(0).email
                + "&receiverList.receiver(0).primary=" + details.receiverList.get(0).primary
                + "&requestEnvelope.errorLanguage=" + getErrorLanguage(details)
                + "&returnUrl=" + details.returnUrl
                + "&senderEmail=" + details.senderEmail
                + "&trackingId=" + details.globalId
                + "&feesPayer=" + PayPalPropeties.PAY_PAL_FEES_PAYER
                + "&memo=" + details.memo
                + "&preapprovalKey=" + (details.preApprovalKey != null ? details.preApprovalKey : "");
    }

    private String getPaymentUrl(PayPalDetails details) {
        return PayPalPropeties.PAY_PAL_ADAPTIVE_PAYMENT_URL
                + "?actionType=" + PayPalPropeties.PAY_PAL_ACTION_TYPE
                + "&cancelUrl=" + details.cancelUrl
                + "&currencyCode=" + PayPalPropeties.PAY_PAL_CURRENCY
                + "&receiverList.receiver(0).amount=" + details.receiverList.get(0).amount
                + "&receiverList.receiver(0).email=" + details.receiverList.get(0).email
                + "&receiverList.receiver(0).primary=" + details.receiverList.get(0).primary
                + "&requestEnvelope.errorLanguage=" + getErrorLanguage(details)
                + "&requestEnvelope.detailLevel=ReturnAll"
                + "&returnUrl=" + details.returnUrl
                + "&memo=" + details.memo
                + "&trackingId=" + details.globalId;
    }

    private String getPaymentDetailsUrl(String uid, String type) {
        String url = PayPalPropeties.PAY_PAL_PAYMENT_DETAILS_URL;
        url += "?requestEnvelope.errorLanguage=" + getErrorLanguage(new PayPalDetails());
        url += "&requestEnvelope.detailLevel=ReturnAll";
        if (type.equals(PayPalPropeties.PAY_PAL_PAY_KEY)) {
            url += "&payKey=" + uid;
        } else if (type.equals(PayPalPropeties.PAY_PAL_TRANSACTION)) {
            url += "&transactionId=" + uid;
        } else if (type.equals(PayPalPropeties.PAY_PAL_TRACKING_ID)) {
            url += "&trackingId=" + uid;
        }
        return url;
    }

    private String getErrorLanguage(PayPalDetails details) {
        String errorLanguage = PayPalPropeties.PAY_PAL_DEFAULT_ERROR_LANGUAGE;
        if (details.requestEnvelope != null && details.requestEnvelope.errorLanguage != null) {
            errorLanguage = details.requestEnvelope.errorLanguage;
        }
        return errorLanguage;
    }

    @Override
    public void updatePayPalDetails(PayPalDetails details) {
        details.actionType = details.actionType == null ? PayPalPropeties.PAY_PAL_ACTION_TYPE : details.actionType;
        details.returnUrl = details.returnUrl == null ? PayPalPropeties.PAY_PAL_RETURN_URL : details.returnUrl;
        details.cancelUrl = details.cancelUrl == null ? PayPalPropeties.PAY_PAL_CANCEL_URL : details.cancelUrl;
        details.currencyCode = details.currencyCode == null ? PayPalPropeties.PAY_PAL_CURRENCY : details.currencyCode;
        details.feesPayer = details.feesPayer == null ? PayPalPropeties.PAY_PAL_FEES_PAYER : details.feesPayer;
        details.globalId = generateTransactionUID(details);

        List<Receiver> receivers = new ArrayList<Receiver>();
        Receiver receiver = new Receiver();
        receiver.amount = details.amount;
        receiver.email = details.receiverEmail;
        receivers.add(receiver);
        details.receiverList = receivers;
    }

    private boolean isSuccessfulPayment(List<String> response) {
        return response.toString().contains("SUCCESS");
    }

    private static String generateTransactionUID(PayPalDetails details) {
        return MD5Encoder.encode(details.senderEmail + details.receiverEmail + details.amount + new DateTime());
    }
}
