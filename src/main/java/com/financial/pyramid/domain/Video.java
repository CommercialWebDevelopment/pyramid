package com.financial.pyramid.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class Video extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 200)
    public String name;
    @Column(name = "description", nullable = false, length = 500)
    public String description;
    @Column(name = "externalId", nullable = false, length = 200)
    public String externalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
