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
        setTimeout(function () {
            dialog.hide();
        }, this.displayTime);
    },
    confirm: function (message, callback) {
        var dialog = $("#confirmation");
        dialog.find(".modal-body").html(message);
        dialog.css("display", "block");
        dialog.css("margin-top", "-50px");
        dialog.css("margin-left", "-50px");
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
    position:function(dialog){
        dialog.css("left", ($(document).innerWidth() - dialog.width()) / 2);
        dialog.css("top", $(document).scrollTop() + $('body')[0].clientHeight/2-dialog.height());
    }
};