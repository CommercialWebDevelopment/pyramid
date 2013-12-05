package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.AccountDao;
import com.financial.pyramid.domain.Account;
import com.financial.pyramid.service.AccountService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 29.08.13
 * Time: 14:31
 */
@Service("accountService")
@Transactional(readOnly = false)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void activate(Account account, int months) {
        Date newActivationDate = new Date();
        if (!(account.isLocked())) {
            newActivationDate = account.getDateExpired();
        }
        account.setDateActivated(new Date());
        account.setDateExpired(new DateTime(newActivationDate).plusMonths(months).plusDays(1).toDate());
        account.setAppPaid(true);
        update(account);
    }

    @Override
    public void deactivate(Account account) {
        account.setDateActivated(new DateTime().minusMonths(2).toDate());
        account.setDateExpired(new DateTime().minusMonths(1).toDate());
        update(account);
    }

    @Override
    public void update(Account account) {
        accountDao.saveOrUpdate(account);
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id);
    }
}
