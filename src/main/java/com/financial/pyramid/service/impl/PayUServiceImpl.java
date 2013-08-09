package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.PayUService;
import com.financial.pyramid.service.beans.PayUDetails;
import com.financial.pyramid.service.exception.PayUException;
import com.financial.pyramid.utils.HTTPClient;
import com.google.gdata.util.common.base.Pair;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: dbudunov
 * Date: 09.08.13
 * Time: 22:06
 */
@Service("payUService")
@Transactional
public class PayUServiceImpl implements PayUService {

    private static String payUServiceUrl = "https://secure.payu.ru";
    private static String INSTANT_DELIVERY_NOTIFICATION_URL = payUServiceUrl + "/order/idn.php";
    private static String INSTANT_REFUND_NOTIFICATION_URL = payUServiceUrl + "/order/irn.php";

    private static String secretKey = "AABBCCDDEEFF";

    @Override
    public String processPayment(PayUDetails details) throws PayUException {
        List<Pair<String,String>> parameters = getParameters(details);
        List<String> response = HTTPClient.sendRequest(INSTANT_DELIVERY_NOTIFICATION_URL, parameters);
        return null;
    }

    @Override
    public String rejectPayment(PayUDetails details) throws PayUException {
        List<Pair<String,String>> parameters = getParameters(details);
        List<String> response = HTTPClient.sendRequest(INSTANT_REFUND_NOTIFICATION_URL, parameters);
        return null;
    }

    private List<Pair<String,String>> getParameters(PayUDetails details){
        List<Pair<String,String>> parameters = new ArrayList<Pair<String, String>>();
        parameters.add(new Pair<String, String>("MERCHANT",details.getMerchant()));
        parameters.add(new Pair<String, String>("ORDER_REF", details.getReference()));
        parameters.add(new Pair<String, String>("ORDER_AMOUNT",details.getAmount()));
        parameters.add(new Pair<String, String>("ORDER_CURRENCY",details.getCurrency()));
        parameters.add(new Pair<String, String>("IRN_DATE", details.getDate()));
        parameters.add(new Pair<String, String>("ORDER_HASH", getHash(details)));
        parameters.add(new Pair<String, String>("REF_URL", details.getReferenceUrl()));
        return parameters;
    }

    private String getHash(PayUDetails details){
        StringBuilder hashString = new StringBuilder();
        hashString.append(details.getMerchant().length());
        hashString.append(details.getMerchant());
        hashString.append(details.getReference().length());
        hashString.append(details.getReference());
        hashString.append(details.getAmount().length());
        hashString.append(details.getAmount());
        hashString.append(details.getCurrency().length());
        hashString.append(details.getCurrency());
        hashString.append(details.getDate().length());
        hashString.append(details.getDate());
        return hmacMD5(hashString.toString(), secretKey);
    }

    public static String hmacMD5(String value, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes());
            byte[] hexBytes = new Hex().encode(rawHmac);
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
