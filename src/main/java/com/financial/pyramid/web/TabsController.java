package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.AuthenticationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: Danil
 * Date: 05.06.13
 * Time: 23:03
 */
@Controller
@RequestMapping("/pyramid")
public class TabsController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        return "/tabs/home";
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    public ModelAndView training(ModelMap model) {
        List<Video> videos = videoService.find();
        ModelAndView modelAndView = new ModelAndView();
        String defaultVideo = settingsService.getProperty("youTubeVideoUrl", videos.get(0).getExternalId());
        modelAndView.addObject("defaultVideo", defaultVideo);
        modelAndView.addObject("videos", videos);
        modelAndView.setViewName("/tabs/training");
        return modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(ModelMap model) {
        return "/tabs/news";
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String office(ModelMap model) {
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/office";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "/tabs/about";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        return "/tabs/contacts";
    }
}
