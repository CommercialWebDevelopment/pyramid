package com.financial.pyramid.web;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.service.NewsService;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.google.api.client.util.DateTime;
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
 * Date: 21.06.13
 * Time: 19:58
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String get(ModelMap model, @PathVariable Long id){
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "news_settings");
        return "/news";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("news") News news) {
        newsService.save(news);
        QueryForm form = new QueryForm();
        PageForm<News> newsForm = new PageForm<News>();
        List<News> list = newsService.find();
        List<News> newsList = newsService.findByQuery(form);
        newsForm.setRows(newsList);
        newsForm.setTotal(list.size());
        model.addAttribute("newsForm", newsForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "news_settings");
        return "redirect:/pyramid/admin/news_settings";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(ModelMap model, @PathVariable Long id) {
        newsService.remove(id);
        QueryForm form = new QueryForm();
        PageForm<News> newsForm = new PageForm<News>();
        List<News> list = newsService.find();
        List<News> newsList = newsService.findByQuery(form);
        newsForm.setRows(newsList);
        newsForm.setTotal(list.size());
        model.addAttribute("newsForm", newsForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "news_settings");
        return "redirect:/pyramid/admin/news_settings";
    }

}
