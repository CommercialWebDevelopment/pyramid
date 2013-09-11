package com.financial.pyramid.service.impl;

import com.financial.pyramid.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = null;
        if (getLocale().getLanguage().equals("ru")) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
        return dateFormat.format(date);
    }

    @Override
    public String formatDateTime(Date date) {
        SimpleDateFormat dateFormat = null;
        if (getLocale().getLanguage().equals("ru")) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        } else {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        }
        return dateFormat.format(date);
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
