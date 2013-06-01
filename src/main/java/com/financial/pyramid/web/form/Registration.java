package com.financial.pyramid.web.form;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * User: Danil
 * Date: 30.05.13
 * Time: 21:59
 */
public class Registration {

    @NotEmpty
    @Length(min = 10, max = 20)
    private String name;
    @NotEmpty
    @Length(min = 10, max = 20)
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
