package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.VideosForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 16:09
 */
@Controller
@RequestMapping(value = "/video")
public class VideosController {

    @Autowired
    VideoService videoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("video") Video video) {
        videoService.save(video);
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "/tabs/admin/videos";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String save(ModelMap model, @PathVariable Long id) {
        videoService.remove(id);
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "/tabs/admin/videos";
    }
}
