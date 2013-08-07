package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.PerfectMoneyService;
import com.financial.pyramid.service.beans.PerfectMoneyDetails;
import com.financial.pyramid.service.beans.PerfectMoneyHistoryFilter;
import com.financial.pyramid.service.exception.PerfectMoneyException;
import com.financial.pyramid.utils.HTTPClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:44
 */
@Service("perfectMoneyService")
public class PerfectMoneyServiceImpl implements PerfectMoneyService {

    protected static String SPEND_MONEY_URL = "https://perfectmoney.is/acct/confirm.asp";
    protected static String SPEND_MONEY_VERIFY_URL = "https://perfectmoney.is/acct/verify.asp";
    protected static String CODE_CONFIRMATION_URL = "https://perfectmoney.is/acct/protection.asp";
    protected static String VERIFY_ACCOUNT_URL = "https://perfectmoney.is/acct/acc_name.asp";
    protected static String HISTORY_URL = "https://perfectmoney.is/acct/historycsv.asp";
    protected static String BALANCE_URL = "https://perfectmoney.is/acct/balance.asp";
    protected static String RATES_URL = "http://perfectmoney.is/acct/rates.asp";

    @Override
    public String transferMoney(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = SPEND_MONEY_URL + this.getTransferURL(perfectMoneyDetails);
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String transferMoneyWithVerification(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = SPEND_MONEY_VERIFY_URL + this.getTransferURL(perfectMoneyDetails);
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String transferMoneyWithProtectionCode(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException {
        String url = CODE_CONFIRMATION_URL +
                "?AccountID=" + perfectMoneyDetails.accountId +
                "&PassPhrase=" + perfectMoneyDetails.passPhrase +
                "&batch=" + perfectMoneyDetails.batch +
                "&code=" + perfectMoneyDetails.code;
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String verifyAccount(String accountId, String password, String account) throws PerfectMoneyException {
        String url = VERIFY_ACCOUNT_URL + "?" + "AccountID={0}&PassPhrase={1}&Account={2}";
        List<String> response = HTTPClient.sendRequest(url, accountId, password, account);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String getTransferHistory(PerfectMoneyHistoryFilter perfectMoneyHistoryFilter) throws PerfectMoneyException {
        String url = HISTORY_URL +
                "?AccountID=" + perfectMoneyHistoryFilter.accountId +
                "&PassPhrase=" + perfectMoneyHistoryFilter.password +
                "&startmonth=" + perfectMoneyHistoryFilter.startMonth +
                "&startday=" + perfectMoneyHistoryFilter.startDay +
                "&startyear=" + perfectMoneyHistoryFilter.startYear +
                "&endmonth=" + perfectMoneyHistoryFilter.endMonth +
                "&endday=" + perfectMoneyHistoryFilter.endDay +
                "&endyear=" + perfectMoneyHistoryFilter.endYear +
                "&paymentsmade=" + perfectMoneyHistoryFilter.paymentsMade +
                "&paymentsreceived=" + perfectMoneyHistoryFilter.paymentsReceived +
                "&batchfilter=" + perfectMoneyHistoryFilter.batchFilter +
                "&counterfilter=" + perfectMoneyHistoryFilter.counterFilter +
                "&payment_id=" + perfectMoneyHistoryFilter.paymentId +
                "&desc=1";
        List<String> response = HTTPClient.sendRequest(url);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String getAccountBalance(String accountId, String password) throws PerfectMoneyException {
        String url = BALANCE_URL + "?" + "AccountID={0}&PassPhrase={1}";
        List<String> response = HTTPClient.sendRequest(url, accountId, password);
        return processPerfectMoneyResponse(response);
    }

    @Override
    public String getExchangeRates(String currency) {
        return processPerfectMoneyResponse(HTTPClient.sendRequest(RATES_URL + "CUR={0}", currency));
    }

    private String getTransferURL(PerfectMoneyDetails details) {
        return "?AccountID=" + details.accountId +
                "&PassPhrase=" + details.passPhrase +
                "&Payer_Account=" + details.payerAccount +
                "&Payee_Account=" + details.payeeAccount +
                "&Amount=" + details.amount +
                "&Memo=" + details.memo +
                "&PAYMENT_ID=" + details.paymentId +
                "&code=" + details.code +
                "&Period=" + details.period;
    }

    private String processPerfectMoneyResponse(List<String> response) {
        String result = "";
        for (String str : response) {
            if (str.contains("Error")) {
                throw new PerfectMoneyException(str);
            }
            result += str;
        }
        return result;
    }
}
