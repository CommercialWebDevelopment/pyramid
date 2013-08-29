package com.financial.pyramid.service.beans;

/**
 * User: Danil
 * Date: 29.08.13
 * Time: 21:42
 */
public class AccountDetails {
    private int daysLeft = 0;
    private int daysMonth = 0;
    private double earningsSum = 0;

    public int getDaysMonth() {
        return daysMonth;
    }

    public void setDaysMonth(int daysMonth) {
        this.daysMonth = daysMonth;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public double getEarningsSum() {
        return earningsSum;
    }

    public void setEarningsSum(double earningsSum) {
        this.earningsSum = earningsSum;
    }
}
