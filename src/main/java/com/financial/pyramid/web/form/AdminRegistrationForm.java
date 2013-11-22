package com.financial.pyramid.web.form;

import java.util.Date;

/**
 * User: Danil
 * Date: 12.09.13
 * Time: 20:29
 */
public class AdminRegistrationForm extends RegistrationForm {
    private String email;
    private String dateActivated;
    private String dateExpired;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(String dateActivated) {
        this.dateActivated = dateActivated;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }
}
