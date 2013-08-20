package com.financial.pyramid.web;

import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.web.form.SettingsForm;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsController extends AbstractController {

    @Autowired
    private SettingsService settingsService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String load() {
        List<Setting> settingList = settingsService.getProperties();
        Gson gson = new Gson();
        return gson.toJson(settingList);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("settingsForm") SettingsForm settings) {
        settingsService.saveProperties(settings.getSettings());
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "application_settings");
        return "/tabs/admin/settings";
    }
}
