package com.financial.pyramid.service;

public interface SettingsService {

    public String getProperty(String key, String... params);

    public String getProperty(String key);
}
