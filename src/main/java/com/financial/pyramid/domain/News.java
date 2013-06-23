package com.financial.pyramid.domain;

import com.google.api.client.util.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

/**
 * User: dbudunov
 * Date: 21.06.13
 * Time: 19:42
 */
@Entity
@Table(name = "news")
public class News extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 200)
    public String name;

    @Column(name = "date", nullable = false)
    public Date date = new Date();

    @Column(name = "content", nullable = false)
    public String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
