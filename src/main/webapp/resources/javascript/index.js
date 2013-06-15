/**
 * User: Danil
 * Date: 03.06.13
 * Time: 20:04
 */

$(document).ready(function() {
    var tabs = $('#tabs');
    var loader = $("#loader");
    tabs.find('a').on('shown', function (e) {
        loader.show();
        var url = $(e.target).attr("url");
        jQuery.ajax({
            url: url,
            success: function (html) {
                $("#tab-content").html(html);
                loader.hide();
            },
            error: function (html) {
                $("#tab-content").html("");
                loader.hide();
            }
        });
    });
    tabs.find('a:first').tab('show');
});
