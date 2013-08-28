package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * User: dbudunov
 * Date: 27.08.13
 * Time: 15:08
 */
@Service("localizationService")
public class LocalizationServiceImpl implements LocalizationService {

    @Autowired
    MessageSource messageSource;

    @Override
    public String translate(String code) {
        return messageSource.getMessage(code, new String[]{}, getLocale());
    }

    @Override
    public String translate(String code, String... args) {
        return messageSource.getMessage(code, args, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
