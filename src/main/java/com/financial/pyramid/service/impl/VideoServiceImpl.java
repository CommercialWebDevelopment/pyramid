package com.financial.pyramid.service.impl;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private com.financial.pyramid.dao.VideoDao videoDao;

    @Override
    public List<Video> find() {
       return videoDao.findAll();
    }

    @Override
    public void remove(Long id) {
        Video video = videoDao.findById(id);
        videoDao.delete(video);
    }

    @Override
    public void save(Video video) {
        videoDao.saveOrUpdate(video);
    }
}
