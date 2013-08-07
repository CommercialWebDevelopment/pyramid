package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.OperationDao;
import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.service.OperationsLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 23:03
 */
@Service("operationsLoggingService")
@Transactional
public class OperationsLoggingServiceImpl implements OperationsLoggingService {

    @Autowired
    OperationDao operationDao;

    @Override
    public void save(Operation operation) {
        operationDao.saveOrUpdate(operation);
    }
}
