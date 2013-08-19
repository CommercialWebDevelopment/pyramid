package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.SettingsDao;
import com.financial.pyramid.domain.Setting;
import com.financial.pyramid.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

@Service("settingsService")
@Transactional
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsDao settingsDao;

    protected LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();

    public String getProperty(String key, Object ... params) {
        if (this.properties.isEmpty()) {
            initSettings();
        }
        String property = this.properties.get(key);
        return MessageFormat.format(property, params);
    }

    public String getProperty(String key) {
        if (this.properties.isEmpty()) {
            initSettings();
        }
        return this.properties.get(key);
    }

    public List<Setting> getProperties() {
        if (this.properties.isEmpty()) {
            initSettings();
        }
        return settingsDao.findAll();
    }

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public void saveProperties(List<Setting> settings) {
        for(Setting setting : settings){
            settingsDao.saveOrUpdate(setting);
        }
        refresh();
    }

    @Override
    public void refresh() {
        this.properties.clear();
        initSettings();
    }

    private void initSettings() {
        List<Setting> settings = settingsDao.findAll();
        for (Setting setting : settings) {
            this.properties.put(setting.getKeyString(), setting.getValueString());
        }
    }
}
