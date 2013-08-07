package com.financial.pyramid.service;

import com.financial.pyramid.service.beans.PerfectMoneyDetails;
import com.financial.pyramid.service.beans.PerfectMoneyHistoryFilter;
import com.financial.pyramid.service.exception.PerfectMoneyException;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:18
 */
public interface PerfectMoneyService {

    public String transferMoney(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    public String transferMoneyWithVerification(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    public String transferMoneyWithProtectionCode(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    public String verifyAccount(String accountId, String password, String account) throws PerfectMoneyException;

    public String getTransferHistory(PerfectMoneyHistoryFilter perfectMoneyHistoryFilter) throws PerfectMoneyException;

    public String getAccountBalance(String accountId, String password) throws PerfectMoneyException;

    public String getExchangeRates(String currency) throws PerfectMoneyException;
}
