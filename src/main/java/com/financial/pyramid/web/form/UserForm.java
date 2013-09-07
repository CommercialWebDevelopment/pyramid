package com.financial.pyramid.web.form;

/**
 * User: Danil
 * Date: 07.08.13
 * Time: 0:02
 */
public class UserForm {
    private String name;
    private String surname;
    private String phoneNumber;
    private String photo;

    public UserForm(String name, String surname, String phoneNumber, String photo) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
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
}
