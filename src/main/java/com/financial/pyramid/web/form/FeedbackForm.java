package com.financial.pyramid.web.form;

/**
 * User: dbudunov
 * Date: 23.08.13
 * Time: 13:35
 */
public class FeedbackForm {
    public String name;
    public String email;
    public String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
