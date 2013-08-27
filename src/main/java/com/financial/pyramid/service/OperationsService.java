package com.financial.pyramid.service;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.web.form.QueryForm;

import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 23:02
 */
public interface OperationsService {
    public List<Operation> find();
    public List<Operation> get(QueryForm queryForm);
    public void save(Operation operation);
    public Double getTransferredAmount(Date date, Long userId);
}
