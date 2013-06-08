/**
 * User: Danil
 * Date: 03.06.13
 * Time: 20:04
 */

$(document).ready(function() {
    $("#tabs").find("a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
});
