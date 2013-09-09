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
        form.find("#edit-contact-form-label").html('${addContactFormTitle}');
        form.modal("show");
    },
    editForm: function (name, phone, email, id) {
        var form = $("#edit-contact-form");
        form.find("#edit-contact-form-label").html('${editContactFormTitle}');
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
                display: I18N.secondName,
                name: 'name',
                width: 130,
                sortable: true,
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
                display: I18N.confirmed,
                name: 'confirmed',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: I18N.role,
                name: 'role',
                width: 130,
                sortable: true,
                align: 'center'
            }
        ],
        buttons: [
            {
                name: I18N.add,
                bclass: 'add',
                onpress: function (com, grid) {
                    $("#add-user-form").modal("show");
                },
                bimage: '/resources/javascript/flexigrid/css/images/add.png'
            }
            ,
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
                    var passport = selectedRow.passport;
                    $('[name=passportDate]', form).val(passport.date);
                    $('[name=passportIssuedBy]', form).val(passport.issuedBy);
                    $('[name=passportNumber]', form).val(passport.number);
                    $('[name=registeredAddress]', form).val(passport.registeredAddress);
                    $('[name=residenceAddress]', form).val(passport.residenceAddress);
                    $('[name=passportSerial]', form).val(passport.serial);
                },
                bimage: '/resources/javascript/flexigrid/css/images/edit.png'
            }
            ,
            {
                name: I18N.delete,
                bclass: 'delete',
                onpress: function (com, grid) {
                    if (!selectedRow) return;
                    Alert.confirm(I18N.userConfirmDelete, function () {
                        $.get('/user/delete/' + selectedRow.id, {}
                            , function () {
                                ugrid.flexReload();
                            });
                    });
                },
                bimage: '/resources/javascript/flexigrid/css/images/close.png'
            }
            ,
            {
                separator: true
            }
        ],
        searchitems: [
            {
                display: I18N.secondName,
                name: 'name',
                isdefault: true
            }
        ],
        sortname: "name",
        sortorder: "asc",
        usepager: true,
        title: I18N.users,
        useRp: true,
        rp: 15,
        showTableToggleBtn: true,
        height: 200,
        rowClick: function (row, data) {
            var fxgrid = $(".flexigrid");
            if (selectedRow && selectedRow.id == data.id) {
                selectedRow = null;
                fxgrid.find(".edit").css("opacity", 0.3);
                fxgrid.find(".delete").css("opacity", 0.3);
            } else {
                selectedRow = data;
                fxgrid.find(".edit").css("opacity", 1);
                fxgrid.find(".delete").css("opacity", 1);
            }
        }
    });

});