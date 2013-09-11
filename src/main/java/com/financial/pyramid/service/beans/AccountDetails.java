package com.financial.pyramid.service.beans;

import java.util.Date;

/**
 * User: Danil
 * Date: 29.08.13
 * Time: 21:42
 */
public class AccountDetails {
    private Integer daysLeft;
    private Integer daysMonth;
    private Date dateExpired;
    private Date dateActivated;
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

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }
}
