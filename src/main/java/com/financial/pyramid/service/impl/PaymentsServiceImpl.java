package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Operation;
import com.financial.pyramid.service.*;
import com.financial.pyramid.service.beans.PerfectMoneyDetails;
import com.financial.pyramid.service.exception.PerfectMoneyException;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.web.form.QueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 22:47
 */
@Service("paymentsService")
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    OperationsService operationsService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    ApplicationConfigurationService applicationConfigurationService;

    @Override
    public boolean isTransferLimitReached(Date date, Long userId) {
        Double transferLimit = Double.valueOf(settingsService.getProperty(Setting.MAX_ALLOWED_TRANSFER_AMOUNT_PER_DAY));
        Double transferredSum = operationsService.getTransferredAmount(date, userId);
        return transferredSum >= transferLimit;
    }

    @Override
    public Double allowedToBeTransferred(Date date, Long userId) {
        Double transferLimit = Double.valueOf(settingsService.getProperty(Setting.MAX_ALLOWED_TRANSFER_AMOUNT_PER_DAY));
        Double transferredSum = operationsService.getTransferredAmount(date, userId);
        Double sum = transferLimit - transferredSum;
        return new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
