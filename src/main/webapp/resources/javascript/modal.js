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
}