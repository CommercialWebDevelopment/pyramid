$(document).ready(function () {
    var treePanel = $("#tree-panel");
    var form = $("#user-email-form");
    var tree = $("#users-tree");
    var sidebar = $("#sidebar");
    var parentId = $("#tree").attr("parent").replace("user-", "");
    var topUser = $("#top-user");
    var userId = parentId;
    var mode = "huge";

    var initTree = function() {
        $(".popover").remove();
        var users = $(".user-photo");
        var stubNodes = $(".stub-node");
        users.click(function () {
            userId = $(this).attr("id").replace("user-", "");
            updateTree();
            topUser.show();
        });
        users.popover({placement: "right", animation: true, html: true, trigger: "hover", container: 'body'});
        stubNodes.popover({placement: "right", animation: true, html: true, trigger: "hover", container: 'body'});
        stubNodes.click(function () {
            form.find("#parentId").val($(this).attr("parentId"));
            form.find("#position").val($(this).attr("position"));
            form.modal("show");
        });

        if (users.length > 0 && tree.length > 0) {
            var parentEl = users.parent();
            if (parentEl.length > 0) {
                tree.scrollLeft(parentEl.width() / 2 - tree.width() / 2);
            }
        }
    };

    var updateTree = function() {
        $.get( "office/"+userId+"?mode="+mode, function(data) {
            treePanel.next().remove();
            treePanel.after(data);
            initTree();
        });
    };

    topUser.hide();
    topUser.click(function () {
        userId = parentId;
        updateTree();
        topUser.hide();
    });

    $("#usersLarge").click(function () {
        mode = "huge";
        updateTree();
    });

    $("#usersSmall").click(function () {
        mode = "small";
        updateTree();
    });

    var viewHuge = function (element) {
        element.removeClass("icon-resize-full");
        element.addClass("icon-resize-small");
        element.attr("title", I18N.compactView);
        tree.parent().removeClass("span8");
        tree.parent().addClass("span12");
        sidebar.hide();
    };
    var viewSmall = function (element) {
        element.removeClass("icon-resize-small");
        element.addClass("icon-resize-full");
        element.attr("title", I18N.extendedView);
        tree.parent().removeClass("span12");
        tree.parent().addClass("span8");
        sidebar.show();
    };

    $("#viewSwitcher").click(function () {
        if ($(this).hasClass("icon-resize-full")) {
            viewHuge($(this));
        } else {
            viewSmall($(this));
        }
    });

    $("#addUser").click(function () {
        form.modal("show");
    });

    initTree();
});