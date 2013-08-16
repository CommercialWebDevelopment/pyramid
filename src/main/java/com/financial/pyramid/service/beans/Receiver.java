package com.financial.pyramid.service.beans;

/**
 * User: dbudunov
 * Date: 16.08.13
 * Time: 15:40
 */
public class Receiver {
    public String amount;
    public String email;
    public boolean primary = false;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
