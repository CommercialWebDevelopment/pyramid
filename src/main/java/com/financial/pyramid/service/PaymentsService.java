package com.financial.pyramid.service;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:44
 */
public interface PaymentsService {
    public boolean isTransferLimitReached(Date date, Long userId);
    public Double allowedToBeTransferred(Date date, Long userId);
}
