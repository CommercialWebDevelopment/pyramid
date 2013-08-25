package com.financial.pyramid.domain;

import com.financial.pyramid.domain.type.Role;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Danil
 * Date: 28.05.13
 * Time: 21:20
 */

@Entity
@Table(name = "user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractEntity implements Serializable {

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "password", nullable = true, length = 250)
    private String password;

    @Column(name = "surname", nullable = false, length = 200)
    private String surname;

    @Column(name = "patronymic", nullable = false, length = 200)
    private String patronymic;

    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 50)
    private String phoneNumber;

    private Passport passport;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "owner_id", nullable = true)
    private Long ownerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "left_child_id", nullable = true)
    private User leftChild;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "right_child_id", nullable = true)
    private User rightChild;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public User getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(User leftChild) {
        this.leftChild = leftChild;
    }

    public User getRightChild() {
        return rightChild;
    }

    public void setRightChild(User rightChild) {
        this.rightChild = rightChild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getShortName() {
        return this.name + " " + this.surname;
    }

    public String getFullName() {
        return this.surname + " " + this.name + " " + this.patronymic;
    }
}
