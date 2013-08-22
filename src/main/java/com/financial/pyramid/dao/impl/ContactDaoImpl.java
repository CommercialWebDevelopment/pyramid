package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.ContactDao;
import com.financial.pyramid.domain.Contact;
import org.springframework.stereotype.Repository;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 13:56
 */
@Repository(value = "contactDao")
public class ContactDaoImpl extends AbstractDaoImpl<Contact, Long> implements ContactDao {

    protected ContactDaoImpl() {
        super(Contact.class);
    }
}
