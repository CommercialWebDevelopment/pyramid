package com.financial.pyramid.service;

import java.util.Date;

/**
 * User: dbudunov
 * Date: 27.08.13
 * Time: 15:07
 */
public interface LocalizationService {
    public String translate(String code);
    public String translate(String code, String... args);
    public String formatDate(Date date);
    public String formatDateTime(Date date);
}
