package com.financial.pyramid.domain;

import com.financial.pyramid.domain.type.TransactionType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * User: Danil
 * Date: 25.08.13
 * Time: 0:46
 */
@Entity
@Table(name = "account")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Account extends AbstractEntity implements Serializable {

    @Basic
    @Column(name = "count")
    private Long count = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="account", cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Transient
    public void writeOFF(Long count) {
        this.count -= count;
        Transaction transaction = new Transaction();
        transaction.setAccount(this);
        transaction.setType(TransactionType.OUT);
        transaction.setCount(count);
        transactions.add(transaction);
    }

    @Transient
    public void writeIN(Long count) {
        this.count += count;
        Transaction transaction = new Transaction();
        transaction.setAccount(this);
        transaction.setType(TransactionType.IN);
        transaction.setCount(count);
        transactions.add(transaction);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCount() {
        return count;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
