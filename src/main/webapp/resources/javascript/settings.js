var Settings = {
    data: {},
    getProperty: function (key, params) {
        var result = null;
        if (this.data && this.data[key]) {
            var setting = this.data[key];
            if (setting && params) {
                result = this.format(setting, params).toString();
            } else {
                result = setting;
            }
        }
        return result;
    },
    setProperty: function (key, value) {
        this.data[key] = value;
    },
    format: function (string, args) {
        var s = string;
        for (var i = 0; i < args.length; i++) {
            s = s.replace("{" + i + "}", args[i]);
        }
        return s;
    }
};
$(document).ready(function () {
    jQuery.ajax({
        url: "/settings/",
        success: function (response) {
            var settings = jQuery.parseJSON(response);
            for (var i = 0; i < settings.length; i++) {
                Settings.setProperty(settings[i].keyString, settings[i].valueString);
            }
        },
        error: function (response) {
        }
    });
});