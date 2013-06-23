package com.financial.pyramid.service;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.exception.UserAlreadyExistsException;
import com.financial.pyramid.web.form.RegistrationForm;

/**
 * User: Danil
 * Date: 01.06.13
 * Time: 20:52
 */
public interface RegistrationService {
    
    public boolean registration(RegistrationForm form, boolean confirm) throws UserAlreadyExistsException;
}
