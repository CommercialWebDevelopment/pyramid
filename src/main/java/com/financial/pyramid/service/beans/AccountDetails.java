package com.financial.pyramid.service.beans;

/**
 * User: Danil
 * Date: 29.08.13
 * Time: 21:42
 */
public class AccountDetails {
    private Integer daysLeft;
    private Integer daysMonth;
    private double balance;
    private boolean appPaid;

    public boolean isAppPaid() {
        return appPaid;
    }

    public void setAppPaid(boolean appPaid) {
        this.appPaid = appPaid;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Integer getDaysMonth() {
        return daysMonth;
    }

    public void setDaysMonth(Integer daysMonth) {
        this.daysMonth = daysMonth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
