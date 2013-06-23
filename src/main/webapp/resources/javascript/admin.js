$(document).ready(function () {
    var selectedRow = null;
    $("#user-grid").flexigrid({
        url: '/user/list',
        dataType: 'json',
        method: 'GET',
        resizable:false,
        colModel: [
            {
                display: 'Name',
                name: 'name',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: 'Surname',
                name: 'surname',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: 'Patronymic',
                name: 'patronymic',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: 'email',
                name: 'email',
                width: 130,
                sortable: true,
                align: 'center'
            },
            {
                display: 'confirmed',
                name: 'confirmed',
                width: 130,
                sortable: true,
                align: 'center'
            }
        ],
        buttons: [
            {
                name: 'Добавить',
                bclass: 'add',
                onpress: function(com, grid) {
                    $("#add-user-form").modal("show");
                },
                bimage:'/resources/javascript/flexigrid/css/images/add.png'
            }
            ,
            {
                name: 'Редактировать',
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
                name: 'Удалить',
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
                display: 'EmployeeID',
                name: 'employeeID'
            },
            {
                display: 'Name',
                name: 'name',
                isdefault: true
            }
        ],
        sortname: "name",
        sortorder: "asc",
        usepager: true,
        title: 'Пользователи',
        useRp: true,
        rp: 15,
        showTableToggleBtn: true,
        height: 200,
        rowClick: function (row, data) {
            selectedRow = data;
        }
    });

    function Example4(com, grid) {
        if (com == 'Delete') {
            var conf = confirm('Delete ' + $('.trSelected', grid).length + ' items?')
            if (conf) {
                $.each($('.trSelected', grid),
                    function (key, value) {
                        $.get('example4.php', { Delete: value.firstChild.innerText}
                            , function () {
                                // when ajax returns (callback), update the grid to refresh the data
                                $(".flexme4").flexReload();
                            });
                    });
            }
        }
        else if (com == 'Edit') {
            var conf = confirm('Edit ' + $('.trSelected', grid).length + ' items?')
            if (conf) {
                $.each($('.trSelected', grid),
                    function (key, value) {
                        // collect the data
                        var OrgEmpID = value.children[0].innerText; // in case we're changing the key
                        var EmpID = prompt("Please enter the New Employee ID", value.children[0].innerText);
                        var Name = prompt("Please enter the Employee Name", value.children[1].innerText);
                        var PrimaryLanguage = prompt("Please enter the Employee's Primary Language", value.children[2].innerText);
                        var FavoriteColor = prompt("Please enter the Employee's Favorite Color", value.children[3].innerText);
                        var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal", value.children[4].innerText);

                        // call the ajax to save the data to the session
                        $.get('example4.php',
                            { Edit: true, OrgEmpID: OrgEmpID, EmpID: EmpID, Name: Name, PrimaryLanguage: PrimaryLanguage, FavoriteColor: FavoriteColor, FavoritePet: FavoriteAnimal  }
                            , function () {
                                // when ajax returns (callback), update the grid to refresh the data
                                $(".flexme4").flexReload();
                            });
                    });
            }
        }
        else if (com == 'Add') {
            // collect the data
            var EmpID = prompt("Please enter the Employee ID", "5");
            var Name = prompt("Please enter the Employee Name", "Mark");
            var PrimaryLanguage = prompt("Please enter the Employee's Primary Language", "php");
            var FavoriteColor = prompt("Please enter the Employee's Favorite Color", "Tan");
            var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal", "Dog");

            // call the ajax to save the data to the session
            $.get('example4.php', { Add: true, EmpID: EmpID, Name: Name, PrimaryLanguage: PrimaryLanguage, FavoriteColor: FavoriteColor, FavoritePet: FavoriteAnimal  }
                , function () {
                    // when ajax returns (callback), update the grid to refresh the data
                    $(".flexme4").flexReload();
                });
        }
    }

});