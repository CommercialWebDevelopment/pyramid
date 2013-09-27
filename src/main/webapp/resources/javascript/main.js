var Alert = {
    INFO: "INFO",
    WARNING: "WARNING",
    SUCCESS: "SUCCESS",
    ERROR: "ERROR",
    informationDialog: null,
    warningDialog: null,
    errorDialog: null,
    successDialog: null,
    displayTime: 3000,
    show: function (type, message) {
        var dialog = this.getDialog(type);
        dialog.find(".text").html(message);
        dialog.css("display", "block");
        dialog.css("top", "5px");
        dialog.css("zIndex", "1000");
        this.position(dialog);
        dialog.find(".close").on("click", function (e) {
            e.stopPropagation();
            dialog.hide();
        });
        setTimeout(function () {
            dialog.hide();
        }, this.displayTime);
    },
    confirm: function (message, callback) {
        var dialog = $("#confirmation");
        dialog.find(".modal-body").html(message);
        dialog.css("display", "block");
        dialog.find("#yesButton").on("click", function (e) {
            if (callback) {
                callback();
                dialog.modal("hide");
            }
        });
        dialog.find("#noButton").on("click", function (e) {
            dialog.modal("hide");
        });
        dialog.find(".close").on("click", function (e) {
            e.stopPropagation();
            dialog.modal("hide");
        });
        dialog.modal("show");
    },
    getDialog: function (type) {
        var dialog;
        if (type == this.INFO) {
            dialog = this.informationDialog ? this.informationDialog : $("#alert-info");
        }
        if (type == this.WARNING) {
            dialog = this.warningDialog ? this.warningDialog : $("#alert-warning");
        }
        if (type == this.SUCCESS) {
            dialog = this.successDialog ? this.successDialog : $("#alert-success");
        }
        if (type == this.ERROR) {
            dialog = this.errorDialog ? this.errorDialog : $("#alert-error");
        }
        return dialog;
    },
    position: function (dialog) {
        dialog.css("left", ($(document).innerWidth() - dialog.width()) / 2);
        dialog.css("top", $(document).scrollTop() + $('body')[0].clientHeight / 2 - dialog.height());
    }
};
var LoadingBar = {
    loader: null,
    show: function (text) {
        this.getPosition();
        this.getLoader().find(".loader-text").html(text);
        this.getLoader().show();
    },
    hide: function () {
        this.getLoader().hide();
    },
    getLoader: function () {
        if (this.loader == null) {
            this.loader = $("#loader");
        }
        return this.loader;
    },
    getPosition: function () {
        var loader = this.getLoader();
        loader.css("left", ($(document).innerWidth() - loader.width()) / 2);
        loader.css("top", $(document).scrollTop() + $('body')[0].clientHeight / 2 - loader.height());
    }
};
var Modal = {
    show: function (modal, options) {
        var width, height, left, top;
        if (!options) {
            options = {
                width: $(document).innerWidth() + "px",
                height: $(document).innerHeight() + "px",
                left: "0px",
                top: "0px",
                align: "none"
            }
        }
        modal.css("width", options.width);
        modal.css("height", options.height);
        if (options.align == "none" || options.align == "left") {
            modal.css("top", "0px");
            modal.css("left", "0px");
        } else if (options.align == "right") {
            modal.css("right", "0px");
            modal.css("top", "0px");
        } else if (options.align == "center") {
            modal.css("left", ($(document).innerWidth() - modal.width()) / 2);
            modal.css("top", $(document).scrollTop() + $('body')[0].clientHeight / 2 - modal.height());
        }
        modal.css("zIndex", 10002);
        modal.find(".modal-body").css("max-height", "none");
        modal.modal("show");
    }
};
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
var EarningsReport = {
    options: {
        lines: {
            show: true
        },
        points: {
            show: true
        },
        xaxis: {
            tickDecimals: 0,
            tickSize: 1
        },
        yaxis: {
            tickDecimals: 2,
            tickSize: 0
        },
        grid: {
            hoverable: true
        }
    },
    url: "/reports/earnings",
    data: [],
    fetchedData: {},
    prevSelected: 3,
    load: function (period) {
        var scope = this;
        if ($.plot && $("#placeholder").length > 0) {
            $.plot("#placeholder", this.data, this.options);
        }
        period = !period || period == 0 ? 3 : period;
        var prevSelector = $("#period_" + this.prevSelected);
        var nextSelector = $("#period_" + period);
        if (prevSelector) prevSelector.removeClass("active");
        if (nextSelector) nextSelector.addClass("active");
        this.prevSelected = period;
        $.ajax({
            url: scope.url + "/" + period,
            type: "GET",
            dataType: "json",
            success: function (report) {
                if (!scope.fetchedData[report.label]) {
                    scope.fetchedData[report.label] = true;
                    scope.data.push(report);
                }
                if ($.plot && $("#placeholder").length > 0) {
                    $.plot("#placeholder", scope.data, scope.options);
                }
                scope.fetchedData = {};
                scope.data = [];
            }
        });
    }
};
var VideosPage = {
    load: function (url) {
        var player = $("#player");
        player[0].src = url;
        $(document).scrollTop(player[0].scrollTop + 65);
    }
};
var ContactsPage = {
    beforeSubmit: function () {
        var form = $("#feedbackForm");
        var emailField = form.find("#emailField");
        var nameField = form.find("#nameField");
        if (emailField.val() == "" || nameField.val() == "") {
            Alert.show(Alert.ERROR, I18N.feedbackFormError);
            return false;
        } else {
            return true;
        }
    }
};
var RestorePasswordPage = {
    beforeSubmit: function () {
        var form = $("#restorePasswordForm");
        var email = form.find("#email");
        Form.validateEMailField(email, email.val());
        if (!Form.validateForm(form)) {
            return false;
        } else {
            return true;
        }
    }
};
var BuyOfficePage = {
    beforeSubmit: function () {
        var form = $("#payForm");
        var emailField = form.find("#emailField");
        Form.validateEMailField(emailField, emailField.val());
        if (!Form.validateForm(form)) {
            Alert.show(Alert.ERROR, I18N.incorrectFields);
            return false;
        } else {
            LoadingBar.show(I18N.sendToPayPal);
            return true;
        }
    }
};
var PayOfficePage = {
    beforeSubmit: function () {
        var form = $("#payForm");
        var emailField = form.find("#emailField");
        Form.validateEMailField(emailField, emailField.val());
        if (!Form.validateForm(form)) {
            Alert.show(Alert.ERROR, I18N.incorrectFields);
            return false;
        } else {
            LoadingBar.show(I18N.sendToPayPal);
            return true;
        }
    }
};
var SendMoneyPage = {
    beforeSubmit: function (amount) {
        var form = $("#takeMoneyForm");
        var emailField = form.find("#emailField");
        var amountField = form.find("#amountField");
        Form.validateEMailField(emailField, emailField.val());
        Form.validateFloatField(amountField, amountField.val());
        var invalidSum = amountField.val() > amount;
        if (invalidSum) {
            Form.setInvalidField(amountField);
            Alert.show(Alert.ERROR, I18N.invalidSum);
            return false;
        } else if (!Form.validateForm(form)) {
            Alert.show(Alert.ERROR, I18N.incorrectFields);
            return false;
        } else {
            LoadingBar.show(I18N.sendToPayPal);
            return true;
        }
    }
};
var UserSettingsPage = {
    beforeSubmit: function () {
        var form = $("#email-form");
        var email1 = form.find("#email1");
        var email2 = form.find("#email2");
        Form.validateEMailField(email1, email1.val());
        Form.validateEMailField(email2, email2.val());
        var valid = (email1.val() != "" && email2.val() != "") && email1.val() == email2.val();
        if (!Form.validateForm(form) || !valid) {
            return false;
        } else {
            return true;
        }
    }
};
var Copyright = {
    get: function () {
        var date = new Date().getFullYear();
        if (date > 2013) {
            document.write("2013 -" + date.toString());
        } else {
            document.write(date.toString());
        }
    }
};
$(document).ready(function () {
    var scroll = $('.page-scroll');
    scroll.mouseover(function () {
        scroll.addClass("over");
    });
    scroll.mouseout(function () {
        scroll.removeClass("over");
    });
    $(window).scroll(function () {
        if ($(this).scrollTop() > 150) {
            scroll.fadeIn();
        } else {
            scroll.fadeOut();
        }
    });
    scroll.click(function () {
        $("html, body").animate({ scrollTop: 0 }, 600);
        return false;
    });
});