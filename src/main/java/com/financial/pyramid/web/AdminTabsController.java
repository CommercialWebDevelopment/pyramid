package com.financial.pyramid.web;

import com.financial.pyramid.domain.*;
import com.financial.pyramid.service.RegistrationService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.RegistrationForm;
import com.financial.pyramid.web.form.SettingsForm;
import com.financial.pyramid.web.form.VideosForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 11:56
 */
@Controller
@RequestMapping("/pyramid/admin")
@Secured({"ADMIN", "SUPER_ADMIN"})
public class AdminTabsController extends TabsController {

    private final static Logger logger = Logger.getLogger(AdminTabsController.class);

    @RequestMapping(value = "/user_settings", method = RequestMethod.GET)
    public String users(ModelMap model){
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "user_settings");
        model.addAttribute("registration", new RegistrationForm());
        return "/tabs/admin/users";
    }

    @RequestMapping(value = "/content_settings", method = RequestMethod.GET)
    public String content(ModelMap model){
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "content_settings");
        return "/tabs/admin/content";
    }

    @RequestMapping(value = "/video_settings", method = RequestMethod.GET)
    public String videos(ModelMap model){
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        List<Video> videos = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videos);
        model.addAttribute("videosForm", videosForm);
        return "/tabs/admin/videos";
    }

    @RequestMapping(value = "/contact_settings", method = RequestMethod.GET)
    public String contacts(ModelMap model){
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "contact_settings");
        return "/tabs/admin/contacts";
    }

    @RequestMapping(value = "/application_settings", method = RequestMethod.GET)
    public String settings(ModelMap model){
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "application_settings");
        List<Setting> settings = settingsService.getProperties();
        SettingsForm settingsForm = new SettingsForm();
        settingsForm.setSettings(settings);
        model.addAttribute("settingsForm", settingsForm);
        return "/tabs/admin/settings";
    }
}
