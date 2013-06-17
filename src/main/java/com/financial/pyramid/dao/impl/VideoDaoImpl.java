package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.VideoDao;
import com.financial.pyramid.domain.Video;
import org.springframework.stereotype.Repository;

@Repository(value = "videoDao")
public class VideoDaoImpl extends AbstractDaoImpl<Video, Long> implements VideoDao {

    protected VideoDaoImpl() {
        super(Video.class);
    }
}
