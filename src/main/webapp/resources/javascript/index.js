/**
 * User: Danil
 * Date: 03.06.13
 * Time: 20:04
 */

$(document).ready(function() {
    var content = $("#tab-content");
    var selectedTab = null;
    var loader = $("#loader");
    $("#tabs").find("a").click(function (e) {
        e.preventDefault();
        loader.show();
        var tabElement = $(this);
        tabElement.tab('show');
        var tabContent = content.find(".active");
        if (tabContent.attr("id") == selectedTab){
            return;
        } else {
            tabContent.html("");
        }
        selectedTab = tabContent.attr("id");
        var url = tabElement.attr("url");
        jQuery.ajax({
            url: url,
            success:function(html){
                tabContent.html(html);
                loader.hide();
            },
            error:function(html){
                tabContent.html(html);
                loader.hide();
            }
        });
    });
});
