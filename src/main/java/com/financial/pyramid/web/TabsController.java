package com.financial.pyramid.web;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.NewsService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.tree.BinaryTree;
import com.financial.pyramid.web.form.AuthenticationForm;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: Danil
 * Date: 05.06.13
 * Time: 23:03
 */
@Controller
@RequestMapping("/pyramid")
public class TabsController extends AbstractController {

    @Autowired
    protected VideoService videoService;

    @Autowired
    protected NewsService newsService;

    @Autowired
    protected SettingsService settingsService;

    @Autowired
    protected UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        return "/tabs/home";
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    public String training(ModelMap model) {
        List<Video> videos = videoService.find();
        Video video = videos != null && videos.size() > 0 ? videos.get(0) : null;
        String defaultVideo = settingsService.getProperty("youTubeVideoUrl", video != null ? video.getExternalId() : null);
        model.addAttribute("defaultVideo", defaultVideo);
        model.addAttribute("videos", videos);
        return "/tabs/training";
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(ModelMap model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageForm<News> newsForm = new PageForm<News>();
        QueryForm form = new QueryForm();
        form.setSort("date");
        form.setOrder("desc");
        form.setPage(page);
        int total = newsService.find().size();
        List<News> list = newsService.findByQuery(form);
        newsForm.setPage(page);
        newsForm.setRows(list);
        newsForm.setTotal(total);
        model.addAttribute("newsForm", newsForm);
        return "/tabs/news";
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String office(ModelMap model, HttpSession session, HttpServletRequest request) {
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
//            User currentUser = userService.findByLogin(((UserDetails) principal).getUsername()).get(0);
            BinaryTree userBinaryTree = userService.getUserBinaryTree(null);
            model.addAttribute("userBinaryTree", userBinaryTree);
            return "/tabs/user/private-office";
        }
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/login";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "/tabs/about";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        return "/tabs/contacts";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("registration", new RegistrationForm());
        return "tabs/user/registration-form";
    }
}
