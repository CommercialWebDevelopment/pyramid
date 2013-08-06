package com.financial.pyramid.service;

import com.financial.pyramid.service.beans.PerfectMoneyDetails;
import com.financial.pyramid.service.beans.PerfectMoneyHistoryFilter;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:18
 */
public interface PerfectMoneyService {

    public String transferMoney(PerfectMoneyDetails perfectMoneyDetails);
    public String transferMoneyWithVerification(PerfectMoneyDetails perfectMoneyDetails);
    public String transferMoneyWithProtectionCode(PerfectMoneyDetails perfectMoneyDetails);
    public String verifyAccount(String accountId, String password, String account);
    public String getTransferHistory(PerfectMoneyHistoryFilter perfectMoneyHistoryFilter);
    public String getAccountBalance(String accountId, String password);
    public String getExchangeRates(String currency);
}
