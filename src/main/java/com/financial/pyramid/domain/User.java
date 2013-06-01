package com.financial.pyramid.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: Danil
 * Date: 28.05.13
 * Time: 21:20
 */

@Entity
@Table(name = "user")
public class User extends AbstractEntity implements Serializable{

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

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
}
