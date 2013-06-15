$.alert = {
    INFO: "INFO",
    WARNING: "WARNING",
    SUCCESS: "SUCCESS",
    ERROR: "ERROR",
    informationDialog: null,
    warningDialog: null,
    errorDialog: null,
    successDialog: null,
    displayTime: 5000,
    show: function (type, message) {
        var dialog = this.getDialog(type);
        dialog.find(".text").html(message);
        dialog.css("position", "absolute");
        dialog.css("display", "block");
        dialog.css("top", "5px");
        setTimeout(function () {
            dialog.hide();
        }, this.displayTime);
    },
    getDialog: function (type) {
        var dialog;
        if (type == this.INFO) {
            dialog = this.informationDialog ? this.informationDialog : $(".alert-info");
        }
        if (type == this.WARNING) {
            dialog = this.warningDialog ? this.warningDialog : $(".alert-warning");
        }
        if (type == this.SUCCESS) {
            dialog = this.successDialog ? this.successDialog : $(".alert-success");
        }
        if (type == this.ERROR) {
            dialog = this.errorDialog ? this.errorDialog : $(".alert-error");
        }
        return dialog;
    }
};