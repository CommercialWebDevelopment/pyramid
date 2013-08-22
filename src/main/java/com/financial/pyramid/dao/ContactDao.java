package com.financial.pyramid.dao;

import com.financial.pyramid.domain.Contact;

import java.util.List;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 13:54
 */
public interface ContactDao extends AbstractDao<Contact, Long> {
    public List<Contact> findAll();
}
