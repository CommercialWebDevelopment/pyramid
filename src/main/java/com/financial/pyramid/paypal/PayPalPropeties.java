package com.financial.pyramid.paypal;

/**
 * User: dbudunov
 * Date: 16.08.13
 * Time: 13:07
 */
public class PayPalPropeties {
    public static String PAY_PAL_SERVICE_ENDPOINT = "https://api.sandbox.paypal.com";
    public static String PAY_PAL_PAYMENT_URL = "https://www.sandbox.paypal.com/cgi-bin/webscr";
    public static String PAY_PAL_ADAPTIVE_PAYMENT_URL = "https://svcs.sandbox.paypal.com/AdaptivePayments/Pay";
    public static String PAY_PAL_PAYMENT_DETAILS_URL = "https://svcs.sandbox.paypal.com/AdaptivePayments/PaymentDetails";
    public static String PAY_PAL_CURRENCY = "USD";
    public static String PAY_PAL_ACTION_TYPE = "PAY";
    public static String PAY_PAL_CANCEL_URL = "http://localhost:8080/pyramid/office";
    public static String PAY_PAL_NOTIFY_URL = "http://localhost:8080/paypal/notify";
    public static String PAY_PAL_FEES_PAYER = "EACHRECEIVER";
    public static String PAY_PAL_DEFAULT_ERROR_LANGUAGE = "en_US";
    public static String PAY_PAL_RETURN_URL = "http://localhost:8080/paypal/success";
    public static String PAY_PAL_APPLICATION_ID = "APP-80W284485P519543T";
    public static String PAY_PAL_PAY_KEY = "payPalPayKey";
    public static String PAY_PAL_TRANSACTION = "payPalTransaction";
    public static String PAY_PAL_TRACKING_ID = "payPalTrackingId";
}
