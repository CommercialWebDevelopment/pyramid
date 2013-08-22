package com.financial.pyramid.service;

import com.financial.pyramid.domain.Contact;

import java.util.List;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 13:57
 */
public interface ContactService {
    public List<Contact> findAll();
    public void save(Contact contact);
    public void remove(Long id);
}
