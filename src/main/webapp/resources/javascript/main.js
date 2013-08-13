/**
 * User: Danil
 * Date: 03.06.13
 * Time: 20:04
 */

var options = {
    animation:"true",
    placement:"left",
    content:"Информация..."
};

$(document).ready(function() {
    //$('#user-details').popover(options);
    var scroll = $('.page-scroll');
    scroll.mouseover(function(){
        scroll.addClass("over");
    });
    scroll.mouseout(function(){
        scroll.removeClass("over");
    });

    $(window).scroll(function(){
        if ($(this).scrollTop() > 200) {
            scroll.fadeIn();
        } else {
            scroll.fadeOut();
        }
    });

    scroll.click(function(){
        $("html, body").animate({ scrollTop: 0 }, 600);
        return false;
    });
});
