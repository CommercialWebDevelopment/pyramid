package com.financial.pyramid.web.form;

import java.util.Date;

/**
 * User: Danil
 * Date: 07.08.13
 * Time: 0:02
 */
public class UserForm {
    private String name;
    private String photo;
    private String email;
    private String surname;
    private boolean active;
    private Date birthDate;
    private String bigPhoto;
    private String phoneNumber;

    public UserForm(String name, String surname, String phoneNumber, String photo, String bigPhoto, String email,
                    Date birthDate, boolean active) {
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.active = active;
        this.surname = surname;
        this.bigPhoto = bigPhoto;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBigPhoto() {
        return bigPhoto;
    }

    public void setBigPhoto(String bigPhoto) {
        this.bigPhoto = bigPhoto;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
