package com.financial.pyramid.web;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.NewsService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.form.SettingsForm;
import com.financial.pyramid.web.form.VideosForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 11:56
 */
@Controller
@RequestMapping("/pyramid/admin")
public class AdminTabsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultRequest(ModelMap model) {
        return this.users(model);
    }

    @RequestMapping(value = "/news_settings", method = RequestMethod.GET)
    public String newsDefault(ModelMap model) {
        QueryForm form = new QueryForm();
        PageForm<News> newsForm = new PageForm<News>();
        List<News> list = newsService.find();
        List<News> newsList = newsService.findByQuery(form);
        newsForm.setRows(newsList);
        newsForm.setTotal(list.size());
        model.addAttribute("newsForm", newsForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "news_settings");
        return "/tabs/admin/news";
    }

    @RequestMapping(value = "/news_settings/{page}", method = RequestMethod.GET)
    public String news(ModelMap model, @PathVariable int page) {
        QueryForm form = new QueryForm();
        PageForm<News> newsForm = new PageForm<News>();
        List<News> list = newsService.find();
        List<News> newsList = newsService.findByQuery(form);
        form.setPage(page);
        newsForm.setPage(page);
        newsForm.setRows(newsList);
        newsForm.setTotal(list.size());
        model.addAttribute("newsForm", newsForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "news_settings");
        return "/tabs/admin/news";
    }

    @RequestMapping(value = "/user_settings", method = RequestMethod.GET)
    public String users(ModelMap model) {
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "user_settings");
        return "/tabs/admin/users";
    }

    @RequestMapping(value = "/content_settings", method = RequestMethod.GET)
    public String content(ModelMap model) {
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "content_settings");
        return "/tabs/admin/content";
    }

    @RequestMapping(value = "/video_settings", method = RequestMethod.GET)
    public String videos(ModelMap model) {
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        List<Video> videos = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videos);
        model.addAttribute("videosForm", videosForm);
        return "/tabs/admin/videos";
    }

    @RequestMapping(value = "/contact_settings", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "contact_settings");
        return "/tabs/admin/contacts";
    }

    @RequestMapping(value = "/application_settings", method = RequestMethod.GET)
    public String settings(ModelMap model) {
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "application_settings");
        List<Setting> settings = settingsService.getProperties();
        SettingsForm settingsForm = new SettingsForm();
        settingsForm.setSettings(settings);
        model.addAttribute("settingsForm", settingsForm);
        return "/tabs/admin/settings";
    }
}
