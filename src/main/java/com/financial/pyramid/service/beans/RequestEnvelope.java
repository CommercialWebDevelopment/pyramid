package com.financial.pyramid.service.beans;

/**
 * User: dbudunov
 * Date: 16.08.13
 * Time: 15:40
 */
public class RequestEnvelope {
    public String errorLanguage;
    public String detailLevel;

    RequestEnvelope(String errorLanguage, String detailLevel){
        this.errorLanguage = errorLanguage;
        this.detailLevel = detailLevel;
    }

    public String getErrorLanguage() {
        return errorLanguage;
    }

    public void setErrorLanguage(String errorLanguage) {
        this.errorLanguage = errorLanguage;
    }

    public String getDetailLevel() {
        return detailLevel;
    }

    public void setDetailLevel(String detailLevel) {
        this.detailLevel = detailLevel;
    }
}
