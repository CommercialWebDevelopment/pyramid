package com.financial.pyramid.service;

import com.financial.pyramid.domain.Video;

import java.util.List;

public interface VideoService {
    public List<Video> find();
    public void remove(Long id);
    public void save(Video video);
}
