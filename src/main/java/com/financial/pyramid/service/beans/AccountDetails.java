package com.financial.pyramid.service.beans;

/**
 * User: Danil
 * Date: 29.08.13
 * Time: 21:42
 */
public class AccountDetails {
    private Integer daysLeft;
    private Integer daysMonth;
    private double earningsSum;

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

    public double getEarningsSum() {
        return earningsSum;
    }

    public void setEarningsSum(double earningsSum) {
        this.earningsSum = earningsSum;
    }
}
