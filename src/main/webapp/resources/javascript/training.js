function load(videoId) {
    var url = $.settings.getProperty('youTubeVideoUrl', [videoId]);
    var player = $("#player");
    player[0].src = url;
    $(document).scrollTop(player[0].scrollTop + 50);
}