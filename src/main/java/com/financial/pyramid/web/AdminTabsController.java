package com.financial.pyramid.web;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.NewsService;
import com.financial.pyramid.web.form.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 11:56
 */
@Controller
@RequestMapping("/pyramid/admin")
//@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class AdminTabsController extends TabsController {

    private final static Logger logger = Logger.getLogger(AdminTabsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultRequest(ModelMap model) {
        return this.users(model);
    }

    @RequestMapping(value = "/news_settings", method = RequestMethod.GET)
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
        return "/tabs/admin/news";
    }

    @RequestMapping(value = "/user_settings", method = RequestMethod.GET)
    public String users(ModelMap model) {
        model.addAttribute("registration", new RegistrationForm());
        return "/tabs/admin/users";
    }

    @RequestMapping(value = "/content_settings", method = RequestMethod.GET)
    public String content(ModelMap model) {
        return "/tabs/admin/content";
    }

    @RequestMapping(value = "/video_settings", method = RequestMethod.GET)
    public String videos(ModelMap model) {
        List<Video> videos = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videos);
        model.addAttribute("videosForm", videosForm);
        return "/tabs/admin/videos";
    }

    @RequestMapping(value = "/contact_settings", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        return "/tabs/admin/contacts";
    }

    @RequestMapping(value = "/application_settings", method = RequestMethod.GET)
    public String settings(ModelMap model) {
        List<Setting> settings = settingsService.getProperties();
        SettingsForm settingsForm = new SettingsForm();
        settingsForm.setSettings(settings);
        model.addAttribute("settingsForm", settingsForm);
        return "/tabs/admin/settings";
    }
}
