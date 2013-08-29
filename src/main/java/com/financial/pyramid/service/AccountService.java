package com.financial.pyramid.service;

import com.financial.pyramid.domain.Account;
import org.springframework.stereotype.Service;

/**
 * User: dbudunov
 * Date: 29.08.13
 * Time: 14:30
 */
public interface AccountService {
    public void activate(Account account);
    public void deactivate(Account account);
    public void calculateSum(Account account, Double sum);
    public void update(Account account);
    public Account findById(Long id);
}
