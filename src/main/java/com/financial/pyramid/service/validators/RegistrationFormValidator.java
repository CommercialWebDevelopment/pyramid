package com.financial.pyramid.service.validators;

import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Locale;

/**
 * User: Danil
 * Date: 15.06.13
 * Time: 15:24
 */
public class RegistrationFormValidator implements Validator {

    @Autowired
    private ApplicationContext context;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Locale locale = LocaleContextHolder.getLocale();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "field.required");
    }
}
