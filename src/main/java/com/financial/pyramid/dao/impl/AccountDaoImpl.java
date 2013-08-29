package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.AccountDao;
import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.User;
import org.springframework.stereotype.Repository;

/**
 * User: dbudunov
 * Date: 29.08.13
 * Time: 14:27
 */
@Repository(value = "accountDao")
public class AccountDaoImpl extends AbstractDaoImpl<Account, Long> implements AccountDao {
    protected AccountDaoImpl(){
        super(Account.class);
    }
}
