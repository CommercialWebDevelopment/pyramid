/**
 * User: Danil
 * Date: 04.06.13
 * Time: 22:05
 */
var Form = {
    timeOutId: null,

    validate: function () {
        var fieldsWithError = $("#registration-form").find(".control-group.error");
        var valid = fieldsWithError.length == 0;
        if (!valid) {
            $(fieldsWithError[0]).find("input").focus();
        }
        return valid;
    },

    validateForm: function (form) {
        var fieldsWithError = form.find(".control-group.error");
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

    validateNumberField: function (element, value) {
        this.isNumber($.trim(value)) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validateFloatField: function (element, value) {
        this.isFloat(value) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validateDateField: function (element, value) {
        clearTimeout(this.timeOutId);
        var scope = this;
        this.timeOutId = setTimeout(function () {
            $.ajax({
                type: "GET",
                url: "/user/check_date",
                data: {
                    date: value
                },
                dataType: "text",
                success: function (response, status, xhr) {
                    response == "false" ? scope.setInvalidField(element) : scope.setValidField(element);
                },
                error: function (xhr, status, error) {
                    scope.setInvalidField(element);
                }
            });
        }, 1000);
    },

    validateEMailField: function (element, value) {
        this.isEmail(value) ? this.setValidField(element) : this.setInvalidField(element);
    },

    validatePhoneNumberField: function (element, value) {
        $.isNumeric(value) && value.length >= 6 ? this.setValidField(element) : this.setInvalidField(element);
    },

    isNumber: function (number) {
        var regex = /^[0-9]+$/;
        return regex.test(number);
    },

    isFloat: function (float) {
        var regexp = /^(?=.+)(?:[1-9]\d*|0)?(?:\.\d+)?$/;
        return regexp.test(float);
    },

    isText: function (text) {
        var regex = /^[а-яА-яa-zA-Z]+$/;
        return regex.test(text);
    },

    isEmail: function (email) {
        var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }
};

$(document).ready(function () {
    var userPointer = $(".user-pointer");
    for (var i = 0; i < userPointer.length; i++) {
        var obj = userPointer[i];
        var $obj = $(obj);
        var height = $obj.height();
        var width = $obj.width();
        $obj.attr("height", height);
        $obj.attr("width", width);

        var ctx = obj.getContext('2d');
        ctx.strokeStyle = "black";
        ctx.beginPath();
        var startX = width / 4;
        ctx.lineTo(startX, height);
        ctx.lineTo(width / 2, 0);
        ctx.lineTo(startX * 3, height);
        ctx.stroke();
    }
    var form = $("#user-email-form");
    $(".stub-node").click(function () {
        form.find("#parentId").val($(this).attr("parentId"));
        form.find("#position").val($(this).attr("position"));
        form.modal("show");
    });
});
