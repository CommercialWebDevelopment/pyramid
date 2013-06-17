package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.SettingsDao;
import com.financial.pyramid.domain.Setting;
import org.springframework.stereotype.Repository;

@Repository(value = "settingsDao")
public class SettingsDaoImpl extends AbstractDaoImpl<Setting, Long> implements SettingsDao {

    protected SettingsDaoImpl() {
        super(Setting.class);
    }
}
