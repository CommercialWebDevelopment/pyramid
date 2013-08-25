package com.financial.pyramid.domain;

import com.financial.pyramid.domain.type.Position;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 21:20
 */

@Entity
@Table(name = "invitation",
        uniqueConstraints= {
                @UniqueConstraint(
                name="user_email_index",
                columnNames={"email"}),
                @UniqueConstraint(
                        name="user_global_id_index",
                        columnNames={"global_id"})}
        )
public class Invitation extends AbstractEntity implements Serializable {

    @Column(name = "created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private User parent;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;

    @Column(name = "global_id", updatable = false)
    private String globalId;

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Date getCreated() {
        return created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
}
