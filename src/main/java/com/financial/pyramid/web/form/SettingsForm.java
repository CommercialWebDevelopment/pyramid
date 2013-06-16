package com.financial.pyramid.web.form;

import com.financial.pyramid.domain.Setting;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 13:24
 */
public class SettingsForm {

    public List<Setting> settings;

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }
}
