package com.financial.pyramid.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * User: Danil
 * Date: 15.06.13
 * Time: 14:17
 */
@Embeddable
public class Passport {

    @Column(name = "passport_serial", nullable = true, length = 50)
    private String serial;

    @Column(name = "passport_number", nullable = true, length = 50)
    private String number;

    @Column(name = "passport_date", nullable = true, length = 50)
    private Date date;

    @Column(name = "passport_issued_by", nullable = true, length = 250)
    private String issuedBy;

    @Column(name = "registered_address", nullable = true, length = 250)
    private String registeredAddress;

    @Column(name = "residence_address", nullable = true, length = 250)
    private String residenceAddress;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }
}
