package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.EmailService;
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
    private com.financial.pyramid.service.VideoService videoService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        model.addAttribute("page-name", "home");
        return "/tabs/home";
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    public String training(ModelMap model) {
        List<Video> videos = videoService.find();
        model.addAttribute("videos", videos);
        model.addAttribute("page-name", "training");
        return "/tabs/training";
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(ModelMap model) {
        model.addAttribute("page-name", "news");
        return "/tabs/news";
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String office(ModelMap model) {
        model.addAttribute("authentication", new AuthenticationForm());
        model.addAttribute("page-name", "office");
        return "/tabs/office";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        model.addAttribute("page-name", "about");
        return "/tabs/about";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        model.addAttribute("page-name", "contacts");
        return "/tabs/contacts";
    }
}
