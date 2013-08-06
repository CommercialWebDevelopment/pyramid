package com.financial.pyramid.service.beans;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:31
 */
public class PerfectMoneyHistoryFilter {
    public Long accountId;
    public String password;
    public int startMonth;
    public int startDay;
    public int startYear;
    public int endMonth;
    public int endDay;
    public int endYear;
    public int paymentsMade;
    public int paymentsReceived;
    public int batchFilter;
    public String paymentId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getPaymentsMade() {
        return paymentsMade;
    }

    public void setPaymentsMade(int paymentsMade) {
        this.paymentsMade = paymentsMade;
    }

    public int getPaymentsReceived() {
        return paymentsReceived;
    }

    public void setPaymentsReceived(int paymentsReceived) {
        this.paymentsReceived = paymentsReceived;
    }

    public int getBatchFilter() {
        return batchFilter;
    }

    public void setBatchFilter(int batchFilter) {
        this.batchFilter = batchFilter;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
