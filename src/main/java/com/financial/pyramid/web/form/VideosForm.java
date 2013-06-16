package com.financial.pyramid.web.form;

import com.financial.pyramid.domain.Video;

import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 15:08
 */
public class VideosForm {
    public List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
