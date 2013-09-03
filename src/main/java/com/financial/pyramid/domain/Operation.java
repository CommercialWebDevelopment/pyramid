package com.financial.pyramid.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:26
 */
@Entity
@Table(name = "operations")
public class Operation extends AbstractEntity {

    @Column(name = "type", nullable = false, length = 200)
    public String type;

    @Column(name = "date", nullable = false)
    public Date date;

    @Column(name = "payer", nullable = false, length = 200)
    public String payer;

    @Column(name = "payee", nullable = false, length = 200)
    public String payee;

    @Column(name = "memo", nullable = false, length = 500)
    public String memo;

    @Column(name = "amount", nullable = false)
    public Double amount;

    @Column(name = "error", nullable = false, length = 500)
    public String error;

    @Column(name = "result", nullable = false, length = 500)
    public String result;

    @Column(name = "success", nullable = false)
    public boolean success;

    @Column(name = "user_id", nullable = false)
    public Long userId;

    @Column(name = "global_id", nullable = false)
    public String globalId;

    @Column(name = "completed", nullable = false)
    public boolean completed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
