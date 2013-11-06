$(document).ready(function () {
    var treePanel = $("#tree-panel");
    var form = $("#user-email-form");
    var tree = $("#users-tree");
    var sidebar = $("#sidebar");
    var parentId = $("#tree").attr("parent").replace("user-", "");
    var topUser = $("#top-user");
    var userId = parentId;
    var mode = "huge";

    var initTree = function() {
        $(".popover").remove();
        var users = $(".user-photo");
        var stubNodes = $(".stub-node");
        users.click(function () {
            userId = $(this).attr("id").replace("user-", "");
            updateTree();
            topUser.show();
        });
        users.popover({placement: "right", animation: true, html: true, trigger: "hover", container: 'body'});
        stubNodes.popover({placement: "right", animation: true, html: true, trigger: "hover", container: 'body'});
        stubNodes.click(function () {
            form.find("#parentId").val($(this).attr("parentId"));
            form.find("#position").val($(this).attr("position"));
            form.modal("show");
        });

        if (users.length > 0 && tree.length > 0) {
            var parentEl = users.parent();
            if (parentEl.length > 0) {
                tree.scrollLeft(parentEl.width() / 2 - tree.width() / 2);
            }
        }
    };

    var updateTree = function() {
        $.get( "office/"+userId+"?mode="+mode, function(data) {
            treePanel.next().remove();
            treePanel.after(data);
            initTree();
        });
    };

    topUser.hide();
    topUser.click(function () {
        userId = parentId;
        updateTree();
        topUser.hide();
    });

    $("#usersLarge").click(function () {
        mode = "huge";
        updateTree();
    });

    $("#usersSmall").click(function () {
        mode = "small";
        updateTree();
    });

    var viewHuge = function (element) {
        element.removeClass("icon-resize-full");
        element.addClass("icon-resize-small");
        element.attr("title", I18N.compactView);
        tree.parent().removeClass("span8");
        tree.parent().addClass("span12");
        sidebar.hide();
    };
    var viewSmall = function (element) {
        element.removeClass("icon-resize-small");
        element.addClass("icon-resize-full");
        element.attr("title", I18N.extendedView);
        tree.parent().removeClass("span12");
        tree.parent().addClass("span8");
        sidebar.show();
    };

    $("#viewSwitcher").click(function () {
        if ($(this).hasClass("icon-resize-full")) {
            viewHuge($(this));
        } else {
            viewSmall($(this));
        }
    });

    initTree();

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

    var amountSlider = $('#amountSlider');
    var officePrice = $('#officePrice');
    var appPrice = $('#applicationPrice');
    amountSlider.slider({
        min: 1,
        max: 12,
        step: 1,
        value: 1,
        formater: function (value) {
            return value;
        }
    });
    amountSlider.on('slide', function (ev) {
        var cases = [2, 0, 1, 1, 1, 2];
        var position = 0;
        if (ev.value % 100 > 4 && ev.value % 100 < 20) {
            position = 2;
        } else {
            position = cases[Math.min(ev.value % 10, 5)];
        }
        var v1 = parseFloat(officePrice.val());
        var v2 = appPrice.length > 0 ? parseFloat(appPrice.val()) : 0;
        var total = parseInt(ev.value) * v1 + v2;
        var m = I18N.monthSingle;
        if (position == 1) {
            m = I18N.monthMulti;
        }
        if (position == 2) {
            m = I18N.monthMany;
        }
        $('#totalPrice').val(total.toFixed(2));
        $('#paymentAmount').html(total.toFixed(2));
        $('#officeMonths').val(ev.value);
        $('#monthsCount').html(ev.value + " " + m);
    });
})
;
/* =========================================================
 * bootstrap-slider.js v2.0.0
 * http://www.eyecon.ro/bootstrap-slider
 * =========================================================
 * Copyright 2012 Stefan Petre
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================= */

!function ($) {

    var Slider = function (element, options) {
        this.element = $(element);
        this.picker = $('<div class="slider">' +
            '<div class="slider-track">' +
            '<div class="slider-selection"></div>' +
            '<div class="slider-handle"></div>' +
            '<div class="slider-handle"></div>' +
            '</div>' +
            '<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>' +
            '</div>')
            .insertBefore(this.element)
            .append(this.element);
        this.id = this.element.data('slider-id') || options.id;
        if (this.id) {
            this.picker[0].id = this.id;
        }

        if (typeof Modernizr !== 'undefined' && Modernizr.touch) {
            this.touchCapable = true;
        }

        var tooltip = this.element.data('slider-tooltip') || options.tooltip;

        this.tooltip = this.picker.find('.tooltip');
        this.tooltipInner = this.tooltip.find('div.tooltip-inner');

        this.orientation = this.element.data('slider-orientation') || options.orientation;
        switch (this.orientation) {
            case 'vertical':
                this.picker.addClass('slider-vertical');
                this.stylePos = 'top';
                this.mousePos = 'pageY';
                this.sizePos = 'offsetHeight';
                this.tooltip.addClass('right')[0].style.left = '100%';
                break;
            default:
                this.picker
                    .addClass('slider-horizontal')
                    .css('width', this.element.outerWidth());
                this.orientation = 'horizontal';
                this.stylePos = 'left';
                this.mousePos = 'pageX';
                this.sizePos = 'offsetWidth';
                this.tooltip.addClass('bottom')[0].style.bottom = -this.tooltip.outerHeight() - 14 + 'px';
                break;
        }

        this.min = this.element.data('slider-min') || options.min;
        this.max = this.element.data('slider-max') || options.max;
        this.step = this.element.data('slider-step') || options.step;
        this.value = this.element.data('slider-value') || options.value;
        if (this.value[1]) {
            this.range = true;
        }

        this.selection = this.element.data('slider-selection') || options.selection;
        this.selectionEl = this.picker.find('.slider-selection');
        if (this.selection === 'none') {
            this.selectionEl.addClass('hide');
        }
        this.selectionElStyle = this.selectionEl[0].style;


        this.handle1 = this.picker.find('.slider-handle:first');
        this.handle1Stype = this.handle1[0].style;
        this.handle2 = this.picker.find('.slider-handle:last');
        this.handle2Stype = this.handle2[0].style;

        var handle = this.element.data('slider-handle') || options.handle;
        switch (handle) {
            case 'round':
                this.handle1.addClass('round');
                this.handle2.addClass('round');
                break
            case 'triangle':
                this.handle1.addClass('triangle');
                this.handle2.addClass('triangle');
                break
        }

        if (this.range) {
            this.value[0] = Math.max(this.min, Math.min(this.max, this.value[0]));
            this.value[1] = Math.max(this.min, Math.min(this.max, this.value[1]));
        } else {
            this.value = [ Math.max(this.min, Math.min(this.max, this.value))];
            this.handle2.addClass('hide');
            if (this.selection == 'after') {
                this.value[1] = this.max;
            } else {
                this.value[1] = this.min;
            }
        }
        this.diff = this.max - this.min;
        this.percentage = [
            (this.value[0] - this.min) * 100 / this.diff,
            (this.value[1] - this.min) * 100 / this.diff,
            this.step * 100 / this.diff
        ];

        this.offset = this.picker.offset();
        this.size = this.picker[0][this.sizePos];

        this.formater = options.formater;

        this.layout();

        if (this.touchCapable) {
            // Touch: Bind touch events:
            this.picker.on({
                touchstart: $.proxy(this.mousedown, this)
            });
        } else {
            this.picker.on({
                mousedown: $.proxy(this.mousedown, this)
            });
        }

        if (tooltip === 'show') {
            this.picker.on({
                mouseenter: $.proxy(this.showTooltip, this),
                mouseleave: $.proxy(this.hideTooltip, this)
            });
        } else {
            this.tooltip.addClass('hide');
        }
    };

    Slider.prototype = {
        constructor: Slider,

        over: false,
        inDrag: false,

        showTooltip: function () {
            //this.tooltip.addClass('in');
            //var left = Math.round(this.percent*this.width);
            //this.tooltip.css('left', left - this.tooltip.outerWidth()/2);
            this.over = true;
        },

        hideTooltip: function () {
            if (this.inDrag === false) {
                //this.tooltip.removeClass('in');
            }
            this.over = false;
        },

        layout: function () {
            this.handle1Stype[this.stylePos] = this.percentage[0] + '%';
            this.handle2Stype[this.stylePos] = this.percentage[1] + '%';
            if (this.orientation == 'vertical') {
                this.selectionElStyle.top = Math.min(this.percentage[0], this.percentage[1]) + '%';
                this.selectionElStyle.height = Math.abs(this.percentage[0] - this.percentage[1]) + '%';
            } else {
                this.selectionElStyle.left = Math.min(this.percentage[0], this.percentage[1]) + '%';
                this.selectionElStyle.width = Math.abs(this.percentage[0] - this.percentage[1]) + '%';
            }
            if (this.range) {
                if (this.formater) {
                    this.tooltipInner.text(
                        this.formater(this.value[0]) +
                            ' : ' +
                            this.formater(this.value[1])
                    );
                    this.tooltip[0].style[this.stylePos] = this.size * (this.percentage[0] + (this.percentage[1] - this.percentage[0]) / 2) / 100 - (this.orientation === 'vertical' ? this.tooltip.outerHeight() / 2 : this.tooltip.outerWidth() / 2) + 'px';
                }
            } else {
                if (this.formater) {
                    this.tooltipInner.text(
                        this.formater(this.value[0])
                    );
                    this.tooltip[0].style[this.stylePos] = this.size * this.percentage[0] / 100 - (this.orientation === 'vertical' ? this.tooltip.outerHeight() / 2 : this.tooltip.outerWidth() / 2) + 'px';
                }
            }
        },

        mousedown: function (ev) {

            // Touch: Get the original event:
            if (this.touchCapable && ev.type === 'touchstart') {
                ev = ev.originalEvent;
            }

            this.offset = this.picker.offset();
            this.size = this.picker[0][this.sizePos];

            var percentage = this.getPercentage(ev);

            if (this.range) {
                var diff1 = Math.abs(this.percentage[0] - percentage);
                var diff2 = Math.abs(this.percentage[1] - percentage);
                this.dragged = (diff1 < diff2) ? 0 : 1;
            } else {
                this.dragged = 0;
            }

            this.percentage[this.dragged] = percentage;
            this.layout();

            if (this.touchCapable) {
                // Touch: Bind touch events:
                $(document).on({
                    touchmove: $.proxy(this.mousemove, this),
                    touchend: $.proxy(this.mouseup, this)
                });
            } else {
                $(document).on({
                    mousemove: $.proxy(this.mousemove, this),
                    mouseup: $.proxy(this.mouseup, this)
                });
            }

            this.inDrag = true;
            var val = this.calculateValue();
            this.element.trigger({
                type: 'slideStart',
                value: val
            }).trigger({
                    type: 'slide',
                    value: val
                });
            return false;
        },

        mousemove: function (ev) {

            // Touch: Get the original event:
            if (this.touchCapable && ev.type === 'touchmove') {
                ev = ev.originalEvent;
            }

            var percentage = this.getPercentage(ev);
            if (this.range) {
                if (this.dragged === 0 && this.percentage[1] < percentage) {
                    this.percentage[0] = this.percentage[1];
                    this.dragged = 1;
                } else if (this.dragged === 1 && this.percentage[0] > percentage) {
                    this.percentage[1] = this.percentage[0];
                    this.dragged = 0;
                }
            }
            this.percentage[this.dragged] = percentage;
            this.layout();
            var val = this.calculateValue();
            this.element
                .trigger({
                    type: 'slide',
                    value: val
                })
                .data('value', val)
                .prop('value', val);
            return false;
        },

        mouseup: function (ev) {
            if (this.touchCapable) {
                // Touch: Bind touch events:
                $(document).off({
                    touchmove: this.mousemove,
                    touchend: this.mouseup
                });
            } else {
                $(document).off({
                    mousemove: this.mousemove,
                    mouseup: this.mouseup
                });
            }

            this.inDrag = false;
            if (this.over == false) {
                this.hideTooltip();
            }
            this.element;
            var val = this.calculateValue();
            this.element
                .trigger({
                    type: 'slideStop',
                    value: val
                })
                .data('value', val)
                .prop('value', val);
            return false;
        },

        calculateValue: function () {
            var val;
            if (this.range) {
                val = [
                    (this.min + Math.round((this.diff * this.percentage[0] / 100) / this.step) * this.step),
                    (this.min + Math.round((this.diff * this.percentage[1] / 100) / this.step) * this.step)
                ];
                this.value = val;
            } else {
                val = (this.min + Math.round((this.diff * this.percentage[0] / 100) / this.step) * this.step);
                this.value = [val, this.value[1]];
            }
            return val;
        },

        getPercentage: function (ev) {
            if (this.touchCapable) {
                ev = ev.touches[0];
            }
            var percentage = (ev[this.mousePos] - this.offset[this.stylePos]) * 100 / this.size;
            percentage = Math.round(percentage / this.percentage[2]) * this.percentage[2];
            return Math.max(0, Math.min(100, percentage));
        },

        getValue: function () {
            if (this.range) {
                return this.value;
            }
            return this.value[0];
        },

        setValue: function (val) {
            this.value = val;

            if (this.range) {
                this.value[0] = Math.max(this.min, Math.min(this.max, this.value[0]));
                this.value[1] = Math.max(this.min, Math.min(this.max, this.value[1]));
            } else {
                this.value = [ Math.max(this.min, Math.min(this.max, this.value))];
                this.handle2.addClass('hide');
                if (this.selection == 'after') {
                    this.value[1] = this.max;
                } else {
                    this.value[1] = this.min;
                }
            }
            this.diff = this.max - this.min;
            this.percentage = [
                (this.value[0] - this.min) * 100 / this.diff,
                (this.value[1] - this.min) * 100 / this.diff,
                this.step * 100 / this.diff
            ];
            this.layout();
        }
    };

    $.fn.slider = function (option, val) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('slider'),
                options = typeof option === 'object' && option;
            if (!data) {
                $this.data('slider', (data = new Slider(this, $.extend({}, $.fn.slider.defaults, options))));
            }
            if (typeof option == 'string') {
                data[option](val);
            }
        })
    };

    $.fn.slider.defaults = {
        min: 0,
        max: 10,
        step: 1,
        orientation: 'horizontal',
        value: 5,
        selection: 'before',
        tooltip: 'show',
        handle: 'round',
        formater: function (value) {
            return value;
        }
    };

    $.fn.slider.Constructor = Slider;

}(window.jQuery);