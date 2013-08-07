package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.OperationDao;
import com.financial.pyramid.domain.Operation;
import org.springframework.stereotype.Repository;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:32
 */
@Repository(value = "operationDao")
public class OperationDaoImpl extends AbstractDaoImpl<Operation,Long> implements OperationDao {
    protected OperationDaoImpl(){
        super(Operation.class);
    }
}
