/**
 * User: Danil
 * Date: 04.06.13
 * Time: 22:05
 */
var Registration = {
    timeOutId: null,

    validate: function () {
        var fieldsWithError = $("#registration-form").find(".control-group.error");
        var valid = fieldsWithError.length == 0;
        if (!valid) {
            $(fieldsWithError[0]).find("input").focus();
        }
        return valid;
    },

    setValidField: function (field) {
        var parent = $(field).parents(".control-group");
        parent.removeClass("error");
        parent.addClass("success");
    },

    setInvalidField: function (field) {
        var parent = $(field).parents(".control-group");
        parent.removeClass("success");
        parent.addClass("error");
    },

    validateTextField: function (element, value) {
        $.trim(value).length >= 3 && this.isText($.trim(value)) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validateDateField: function (element, value) {
        this.isDate(value) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validateEMailField: function (element, value) {
        this.isEmail(value) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validatePhoneNumberField: function (element, value) {
        $.isNumeric(value) && value.length >= 6 ? this.setValidField(element) : this.setInvalidField(element);
    },

    validatePasswordField: function (element, value) {
        var regex = /\W/;
        !regex.test(value) && value.length >= 6 ? this.setValidField(element) : this.setInvalidField(element);
    },

    validateLoginField: function (element, value) {
        clearTimeout(this.timeOutId);
        var regex = /^[a-zA-Z1-9_]+$/;
        if (!regex.test(value) || value.length < 6) {
            this.setInvalidField(element);
            return;
        }
        var scope = this;
        this.timeOutId = setTimeout(function(){
            $.ajax({
                type: "GET",
                url: "/user/checkLogin/" + value,
                dataType: "text",
                success: function (response, status, xhr) {
                    response == "false" ? scope.setValidField(element) : scope.setInvalidField(element);
                },
                error: function (xhr, status, error) {
                    scope.setInvalidField(element);
                }
            });
        }, 1000);
    },

    validateSecondPasswordField: function (element, value) {
        var regex = /\W/;
        !regex.test(value) && value.length >= 6 && $("#password").val() == value ? this.setValidField(element) : this.setInvalidField(element);
    },

    isText: function (text) {
        var regex = /^[а-яА-яa-zA-Z]+$/;
        return regex.test(text);
    },

    isDate: function (txtDate) {
        if (txtDate == '') return false;

        var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
        var dtArray = txtDate.match(rxDatePattern);

        if (dtArray == null)
            return false;

        var dtDay = dtArray[1];
        var dtMonth = dtArray[3];
        var dtYear = dtArray[5];

        if (dtMonth < 1 || dtMonth > 12)
            return false;
        else if (dtDay < 1 || dtDay > 31)
            return false;
        else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
            return false;
        else if (dtMonth == 2) {
            var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
            if (dtDay > 29 || (dtDay == 29 && !isleap))
                return false;
        }
        return true;
    },

    isEmail: function (email) {
        var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }
};
