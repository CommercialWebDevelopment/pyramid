package com.financial.pyramid.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * User: Danil
 * Date: 28.05.13
 * Time: 21:28
 */
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    public Long getId() {
        return id;
    }
}
