/**
 * User: Danil
 * Date: 03.06.13
 * Time: 20:04
 */

$(document).ready(function() {
    var content = $("#tab-content");
    $("#tabs").find("a").click(function (e) {
        e.preventDefault();
        var tabElement = $(this);
        tabElement.tab('show');
        var url = tabElement.attr("url");
        var tabContent = content.find(".active");
        jQuery.ajax({
            url: url,
            success:function(html){
                tabContent.html(html);
            },
            error:function(html){
                tabContent.html(html);
            }
        });
    });
});
