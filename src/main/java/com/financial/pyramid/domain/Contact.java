package com.financial.pyramid.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 13:46
 */
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity implements Serializable {

    @Column(name = "phone", nullable = true, length = 250)
    private String phone;

    @Column(name = "person", nullable = true, length = 250)
    private String person;

    @Column(name = "email", nullable = true, length = 250)
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
