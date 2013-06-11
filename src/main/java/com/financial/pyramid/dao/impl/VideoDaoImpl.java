package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.VideoDao;
import com.financial.pyramid.domain.Video;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VideoDaoImpl extends AbstractDaoImpl<Video, Long> implements VideoDao {

    protected VideoDaoImpl() {
        super(Video.class);
    }

    @Override
    public List<Video> findAll() {
        return super.findAll();
    }
}
