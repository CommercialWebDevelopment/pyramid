function load(videoId) {
    var url = 'http://www.youtube.com/embed/' + videoId + '?enablejsapi=1&origin=';
    var player = $("#player");
    player[0].src = url;
    $(document).scrollTop(player[0].scrollTop + 50);
}