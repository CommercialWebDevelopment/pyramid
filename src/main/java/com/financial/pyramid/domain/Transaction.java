package com.financial.pyramid.domain;

import com.financial.pyramid.domain.type.TransactionType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Danil
 * Date: 25.08.13
 * Time: 15:14
 */

@Entity
@Table(name = "transaction")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Transaction extends AbstractEntity implements Serializable {

    @Column(name = "created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Basic
    @Column(name = "count")
    private Long count;

    @Basic
    @Column(name = "balance")
    private Long balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @ManyToOne
    private Account account;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
