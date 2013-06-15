package com.financial.pyramid.web;

import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.service.SettingsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String load() {
        List<Setting> settingList = settingsService.getProperties();
        Gson gson = new Gson();
        return gson.toJson(settingList);
    }
}
