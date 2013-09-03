package com.financial.pyramid.dao;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.web.form.QueryForm;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:26
 */
public interface OperationDao extends AbstractDao<Operation, Long> {
    public List<Operation> findByQuery(QueryForm form);
    public Double getTransferredAmount(Date date, Long userId);
    public void updateStatus(String trackingId, boolean status);
}
