package com.financial.pyramid.domain;

import com.financial.pyramid.domain.type.TransactionType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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
    @Column(name = "balance")
    private Double balance = 0D;

    @Column(name="date_activated", nullable = true)
    private Date dateActivated;

    @Column(name="date_expired", nullable = true)
    private Date dateExpired;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="account", cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Column(name="app_paid", nullable = false, columnDefinition = "boolean default false")
    private boolean appPaid;

    public boolean isAppPaid() {
        return appPaid;
    }

    public void setAppPaid(boolean appPaid) {
        this.appPaid = appPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        if (dateExpired != null ? !dateExpired.equals(account.dateExpired) : account.dateExpired != null) return false;
        if (dateActivated != null ? !dateActivated.equals(account.dateActivated) : account.dateActivated != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = balance != null ? balance.hashCode() : 0;
        result = 31 * result + (dateExpired != null ? dateExpired.hashCode() : 0);
        result = 31 * result + (dateActivated != null ? dateActivated.hashCode() : 0);
        return result;
    }

    @Transient
    public void writeOFF(Double count, String description) {
        this.balance -= count;
        Transaction transaction = new Transaction();
        transaction.setCount(count);
        transaction.setAccount(this);
        transaction.setBalance(this.balance);
        transaction.setDescription(description);
        transaction.setType(TransactionType.OUT);
        transactions.add(transaction);
    }

    @Transient
    public void writeIN(Double count, String description, Long userId) {
        this.balance += count;
        Transaction transaction = new Transaction();
        transaction.setCount(count);
        transaction.setAccount(this);
        transaction.setUserId(userId);
        transaction.setBalance(this.balance);
        transaction.setType(TransactionType.IN);
        transaction.setDescription(description);
        transactions.add(transaction);
    }

    public Double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public boolean isLocked() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return Days.daysBetween(new DateTime(calendar.getTime()), new DateTime(this.dateExpired)).getDays() < 0;
    }
}
