var NewsAdmin = {
    onSubmit: function () {
        var content = $("#editor").html();
        $("#content").val(content);
        $("#editForm").submit();
    },
    beforeAdd: function () {
        var content = $("#editor").html();
        $("#content").val(content);
        $("#addForm").submit();
    },
    addForm: function () {
        Modal.show($("#add-news-form"));
    }
};
var NewsEditor = {
    open: function () {
        var editor = $('#editor');
        editor.wysiwyg();
        editor.cleanHtml();
    }
};
var ContactsAdmin = {
    onSubmit: function () {
        $("#addForm").submit();
    },
    addForm: function () {
        var form = $("#edit-contact-form");
        form.find("#edit-contact-form-label").html(I18N.addContactFormTitle);
        form.modal("show");
    },
    editForm: function (name, phone, email, id) {
        var form = $("#edit-contact-form");
        form.find("#edit-contact-form-label").html(I18N.editContactFormTitle);
        form.find("#person").val(name);
        form.find("#phone").val(phone);
        form.find("#email").val(email);
        form.find("#id").val(id);
        form.modal("show");
    }
};
$(document).ready(function () {
    var selectedRow = null;
    var ugrid = $("#user-grid");
    var callback = function(){
        selectedRow = null;
        ugrid.flexReload();
        var fxgrid = $(".flexigrid");
        fxgrid.find(".edit").css("opacity", 0.3);
        fxgrid.find(".delete").css("opacity", 0.3);
        fxgrid.find(".activate").css("opacity", 0.3);
        fxgrid.find(".deactivate").css("opacity", 0.3);
        fxgrid.css("opacity", 1);
    };
    ugrid.flexigrid({
        url: '/user/list',
        dataType: 'json',
        method: 'GET',
        resizable: false,
        pagetext: I18N.page,
        outof: I18N.of,
        findtext: I18N.find,
        pagestat: I18N.pageStat,
        errormsg: I18N.connectionError,
        procmsg: I18N.pleaseWait,
        nomsg: I18N.noItems,
        colModel: [
            {
                display: 'ID',
                name: 'id',
                width: 30,
                sortable: false,
                align: 'center'
            },
            {
                display: I18N.firstName,
                name: 'surname',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: I18N.secondName,
                name: 'name',
                width: 130,
                sortable: true,
                align: 'center'
            },

            {
                display: I18N.patronymic,
                name: 'patronymic',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: I18N.email,
                name: 'email',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: I18N.dateActivated,
                name: 'dateActivated',
                width: 135,
                sortable: true,
                align: 'center'
            },
            {
                display: I18N.dateExpired,
                name: 'dateExpired',
                width: 135,
                sortable: true,
                align: 'center'
            }
        ],
        buttons: [
//            {
//                name: I18N.add,
//                bclass: 'add',
//                onpress: function (com, grid) {
//                    $("#add-user-form").modal("show");
//                },
//                bimage: '/resources/javascript/flexigrid/css/images/add.png'
//            }
//            ,
            {
                name: I18N.edit,
                bclass: 'edit',
                onpress: function (com, grid) {
                    if (!selectedRow) return;
                    var form = $("#add-user-form");
                    form.modal("show");
                    $.each(selectedRow, function (key, value) {
                        $('[name=' + key + ']', form).val(value);
                    });
                },
                bimage: '/resources/javascript/flexigrid/css/images/edit.png'
            },
            {
                name: I18N.delete,
                bclass: 'delete',
                onpress: function (com, grid) {
                    if (!selectedRow) return;
                    Alert.confirm(I18N.userConfirmDelete, function () {
                        $(".flexigrid").css("opacity", 0.3);
                        $.get('/user/delete/' + selectedRow.id, {}, callback());
                    });
                },
                bimage: '/resources/javascript/flexigrid/css/images/close.png'
            },
            {
                separator: true
            },
            {
                name: I18N.activateUser,
                bclass: 'activate',
                onpress:function(com, grid){
                    if (!selectedRow) return;
                    $(".flexigrid").css("opacity", 0.3);
                    $.get('/user/activate/' + selectedRow.id, {}, callback());
                },
                bimage: '/resources/javascript/flexigrid/css/images/active.png'
            },
            {
                name: I18N.deactivateUser,
                bclass: 'deactivate',
                onpress:function(com, grid){
                    if (!selectedRow) return;
                    $(".flexigrid").css("opacity", 0.3);
                    $.get('/user/deactivate/' + selectedRow.id, {}, callback());
                },
                bimage: '/resources/javascript/flexigrid/css/images/inactive.png'
            }
        ],
        searchitems: [
            {
                display: I18N.secondName,
                name: 'name',
                isdefault: true
            }
        ],
        sortname: "surname",
        sortorder: "asc",
        usepager: true,
        title: I18N.users,
        useRp: true,
        rp: 30,
        showTableToggleBtn: true,
        height: 500,
        rowClick: function (row, data) {
            var fxgrid = $(".flexigrid");
            if (selectedRow && selectedRow.id == data.id) {
                selectedRow = null;
                fxgrid.find(".edit").css("opacity", 0.3);
                fxgrid.find(".delete").css("opacity", 0.3);
                fxgrid.find(".activate").css("opacity", 0.3);
                fxgrid.find(".deactivate").css("opacity", 0.3);
            } else {
                selectedRow = data;
                fxgrid.find(".edit").css("opacity", 1);
                fxgrid.find(".delete").css("opacity", 1);
                fxgrid.find(".activate").css("opacity", 1);
                fxgrid.find(".deactivate").css("opacity", 1);
            }
        }
    });

});