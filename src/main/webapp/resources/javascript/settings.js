var Settings = {
    data: {},
    getProperty: function (key, params) {
        var result = null;
        if (this.data && this.data[key]) {
            var setting = this.data[key];
            if (setting) {
                result = this.format(setting, params).toString();
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