package com.financial.pyramid.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "settings")
public class Setting extends AbstractEntity implements Serializable {

    @Column(name = "keyString", nullable = false, length = 250)
    public String keyString;
    @Column(name = "valueString", nullable = false, length = 250)
    public String valueString;

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
