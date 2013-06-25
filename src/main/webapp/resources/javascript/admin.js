$(document).ready(function () {
    var selectedRow = null;
    $("#user-grid").flexigrid({
        url: '/user/list',
        dataType: 'json',
        method: 'GET',
        resizable:false,
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
            }
        ],
        buttons: [
            {
                name: I18N.add,
                bclass: 'add',
                onpress: function(com, grid) {
                    $("#add-user-form").modal("show");
                },
                bimage:'/resources/javascript/flexigrid/css/images/add.png'
            }
            ,
            {
                name: I18N.edit,
                bclass: 'edit',
                onpress: function(com, grid) {
                    if(!selectedRow) return;
                    var form = $("#add-user-form");
                    form.modal("show");
                    $.each(selectedRow, function(key, value){
                        $('[name='+key+']', form).val(value);
                    });
                    var passport = selectedRow.passport;
                    $('[name=passportDate]', form).val(passport.date);
                    $('[name=passportIssuedBy]', form).val(passport.issuedBy);
                    $('[name=passportNumber]', form).val(passport.number);
                    $('[name=registeredAddress]', form).val(passport.registeredAddress);
                    $('[name=residenceAddress]', form).val(passport.residenceAddress);
                    $('[name=passportSerial]', form).val(passport.serial);
                },
                bimage:'/resources/javascript/flexigrid/css/images/edit.png'
            }
            ,
            {
                name: I18N.delete,
                bclass: 'delete',
                onpress: function(com, grid) {
                    if(!selectedRow) return;
                    $.get('/user/delete/'+selectedRow.id, {}
                        , function () {
                            $("#user-grid").flexReload();
                        });
                },
                bimage:'/resources/javascript/flexigrid/css/images/close.png'
            }
            ,
            {
                separator: true
            }
        ],
        searchitems: [
            {
                display: I18N.second_name,
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
            selectedRow = data;
        }
    });

});