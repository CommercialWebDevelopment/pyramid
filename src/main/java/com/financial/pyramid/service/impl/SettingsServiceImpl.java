package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.SettingsDao;
import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

@Service("settingsService")
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private com.financial.pyramid.dao.SettingsDao settingsDao;

    protected Properties properties = new Properties();

    public void SettingsServiceImpl() {
        List<Setting> settings = settingsDao.findAll();
        for (Setting setting : settings){
            properties.put(setting.getKeyString(), setting.getValueString());
        }
    }

    public String getProperty(String key, String... params) {
        String property = this.properties.getProperty(key);
        return MessageFormat.format(property, params);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}
