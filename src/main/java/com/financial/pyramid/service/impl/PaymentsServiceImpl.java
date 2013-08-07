package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.service.ApplicationConfigurationService;
import com.financial.pyramid.service.OperationsLoggingService;
import com.financial.pyramid.service.PaymentsService;
import com.financial.pyramid.service.PerfectMoneyService;
import com.financial.pyramid.service.beans.PerfectMoneyDetails;
import com.financial.pyramid.service.exception.PerfectMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:47
 */
@Service("paymentsService")
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    PerfectMoneyService perfectMoneyService;

    @Autowired
    OperationsLoggingService operationsLoggingService;

    @Autowired
    ApplicationConfigurationService applicationConfigurationService;

    private static String PERFECT_MONEY_ACCOUNT = "perfectMoneyAccount";
    private static String PERFECT_MONEY_PASSWORD = "perfectMoneyPassword";

    @Override
    public void processPayment(String payee, String memo, Double amount) {
        PerfectMoneyDetails paymentDetails = new PerfectMoneyDetails();
        String paymentId = String.valueOf(System.currentTimeMillis());
        String account = applicationConfigurationService.getParameter(PERFECT_MONEY_ACCOUNT);
        String password = applicationConfigurationService.getParameter(PERFECT_MONEY_PASSWORD);
        paymentDetails.setPayerAccount(account);
        paymentDetails.setPayeeAccount(payee);
        paymentDetails.setMemo(memo);
        paymentDetails.setAmount(amount);
        paymentDetails.setPassPhrase(password);
        paymentDetails.setPaymentId(paymentId);
        boolean isSuccessful = true;
        String paymentResult = "";
        String error = "";
        try {
            paymentResult = perfectMoneyService.transferMoney(paymentDetails);
        } catch (PerfectMoneyException e) {
            isSuccessful = false;
            error = e.getMessage();
        } finally {
            Operation operation = new Operation();
            operation.setType("PAYMENT");
            operation.setDate(new Date());
            operation.setMemo(memo);
            operation.setAmount(amount);
            operation.setPayee(payee);
            operation.setPayer(account);
            operation.setSuccess(isSuccessful);
            operation.setError(error);
            operation.setResult(paymentResult);
            operationsLoggingService.save(operation);
        }
    }
}
