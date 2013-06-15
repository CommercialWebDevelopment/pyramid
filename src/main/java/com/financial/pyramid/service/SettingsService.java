package com.financial.pyramid.service;

import com.financial.pyramid.domain.Setting;

import java.util.List;

public interface SettingsService {

    public String getProperty(String key, Object ... params);

    public String getProperty(String key);

    public List<Setting> getProperties();

    public void setProperty(String key, String value);
}
