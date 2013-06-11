package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.SettingsDao;
import com.financial.pyramid.domain.Setting;

import java.util.List;

public class SettingsDaoImpl extends AbstractDaoImpl<Setting, Long> implements SettingsDao {

    protected SettingsDaoImpl() {
        super(Setting.class);
    }

    @Override
    public List<Setting> findAll() {
        return super.findAll();
    }
}
