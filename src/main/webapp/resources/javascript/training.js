function load(videoId) {
    var url = Settings.getProperty('youTubeVideoUrl', [videoId]);
    var player = $("#player");
    player[0].src = url;
    $(document).scrollTop(player[0].scrollTop + 50);
}