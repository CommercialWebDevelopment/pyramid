package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value="/training")
public class TrainingController {

    @Autowired
    private com.financial.pyramid.service.VideoService videoService;

    @RequestMapping(value = "/")
    public ModelAndView getVideo() {
        List<Video> videos = videoService.find();
        ModelAndView model = new ModelAndView("/training");
        model.addObject("videos", videos);
        return model;
    }
}
