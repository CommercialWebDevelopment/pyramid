package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.ContactDao;
import com.financial.pyramid.domain.Contact;
import com.financial.pyramid.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: dbudunov
 * Date: 22.08.13
 * Time: 13:58
 */
@Service("contactService")
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Override
    public void save(Contact contact) {
        contactDao.saveOrUpdate(contact);
    }

    @Override
    public void remove(Long id) {
        Contact contact = contactDao.findById(id);
        contactDao.delete(contact);
    }
}
