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
}