package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.VideoDao;
import com.financial.pyramid.domain.Video;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VideoDaoImpl implements VideoDao {

    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    //TODO Move sessionFactory to AbstractDao
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Video> findAll() {
        Criteria criteria = getCurrentSession().createCriteria(Video.class);
        return (List<Video>) criteria.list();
    }
}
