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
        var left = !!($obj.attr("drawLeft") === "true");
        var right = !!($obj.attr("drawRight") === "true");

        var ctx = obj.getContext('2d');
        ctx.strokeStyle = "black";
        ctx.beginPath();
        var startX = width / 4;
        if (left) {
            ctx.lineTo(startX, height);
        }
        ctx.lineTo(width / 2, 0);
        if (right) {
            ctx.lineTo(startX * 3, height);
        }
        ctx.stroke();
    }
    var form = $("#user-email-form");
    $(".stub-node").click(function () {
        form.find("#parentId").val($(this).attr("parentId"));
        form.find("#position").val($(this).attr("position"));
        form.modal("show");
    });

    $("#next-button").click(function () {
        if (Form.validate()) {
            $("#user-form-step").hide();
            $("#contract-step").show();
        }
    });
    $("#back-button").click(function () {
        $("#user-form-step").show();
        $("#contract-step").hide();
    });

    $("#contract-offer-accepted").click(function () {
        if (this.checked) {
            $("#registration-form-send-button").attr('disabled', false);
        } else {
            $("#registration-form-send-button").attr('disabled', true);
        }
    });

    var cropImage = $("#crop-image");
    var loadedImageSize = {
        x: 48,
        y: 48
    };
    var avatar = $("#avatar");
    var avatarSRCDefault = avatar.attr("src");
    var select = {
        x1: 0,
        y1: 0,
        x2: 48,
        y2: 48
    };

    // Добавить аватарку
    $("#add-photo").click(function () {
        $("#user-form-step").hide();
        $("#photo-body").show();
        var cropBody = cropImage.parent();
        cropImage.imgAreaSelect({
            handles: true,
            maxWidth: 48,
            maxHeight: 48,
            x1: select.x1,
            y1: select.y1,
            x2: select.x2,
            y2: select.y2,
            parent: cropBody.parent(),
            onSelectEnd: function (img, selection) {
                select = selection;
            },
            onSelectChange: function (img, selection) {
                var scaleX = 48 / selection.width;
                var scaleY = 48 / selection.height;

                avatar.css({
                    width: Math.round(scaleX * loadedImageSize.x),
                    height: Math.round(scaleY * loadedImageSize.y),
                    marginLeft: -Math.round(scaleX * selection.x1),
                    marginTop: -Math.round(scaleY * selection.y1)
                });
            }
        });
    });

    // Выбрать фото с диска
    $("#upload-image-icon").click(function () {
        var file = $("#user-image");
        file.val("");
        file.click();
    });

    // Сохранить аватарку
    $("#save-avatar").click(function () {
        $("#x").val(select.x1);
        $("#y").val(select.y1);
        $("#w").val(select.width);
        $("#h").val(select.height);
        $("#photo-body").hide();
        $("#user-form-step").show();
    });

    // Не сохранить аватарку
    $("#cancel-avatar").click(function () {
        $("#save-avatar").attr('disabled', true);
        avatar.attr("src", avatarSRCDefault);
        cropImage.attr("src", avatarSRCDefault);

        select = {
            x1: 0,
            y1: 0,
            x2: 48,
            y2: 48
        };

        loadedImageSize = {
            x: 48,
            y: 48
        };

        $("#user-image").val("");
        $("#photo-body").hide();
        $("#user-form-step").show();
    });

    // Выбран файл с диска
    $("#user-image").change(function (evt) {
        $("#save-avatar").attr('disabled', false);
        var files = evt.target.files;
        if (files.length == 0) return;
        var file = files[0];
        var reader = new FileReader();
        reader.onload = (function (theFile) {
            return function (e) {
                avatar.get(0).src = e.target.result;
                cropImage.get(0).src = e.target.result;
                loadedImageSize.x = cropImage.width();
                loadedImageSize.y = cropImage.height();
            };
        })(file);
        reader.readAsDataURL(file);
    });
});
