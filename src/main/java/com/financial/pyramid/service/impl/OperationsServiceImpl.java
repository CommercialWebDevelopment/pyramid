package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.OperationDao;
import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.service.OperationsService;
import com.financial.pyramid.web.form.QueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 23:03
 */
@Service("operationsService")
@Transactional
public class OperationsServiceImpl implements OperationsService {

    @Autowired
    OperationDao operationDao;

    @Override
    public List<Operation> find() {
        return operationDao.findAll();
    }

    @Override
    public Operation findByTransactionId(String transactionId) {
        return operationDao.findByTransactionId(transactionId);
    }

    @Override
    public Operation findByGlobalId(String globalId) {
        return operationDao.findByGlobalId(globalId);
    }

    @Override
    public List<Operation> get(QueryForm queryForm) {
        return operationDao.findByQuery(queryForm);
    }

    @Override
    public void save(Operation operation) {
        operationDao.saveOrUpdate(operation);
    }

    @Override
    public void update(String trackingId, boolean status) {
        operationDao.updateStatus(trackingId, status);
    }

   @Override
    public Double getTransferredAmount(Date date, Long userId) {
        return operationDao.getTransferredAmount(date, userId);
    }
}
