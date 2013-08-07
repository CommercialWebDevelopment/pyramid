package com.financial.pyramid.service;

import com.financial.pyramid.domain.Operation;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 23:02
 */
public interface OperationsLoggingService {

    public void save(Operation operation);
}
