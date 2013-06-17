$(document).ready(function() {

$("#user-grid").flexigrid({
    url : '/user/list',
    dataType : 'json',
    method: 'GET',
    colModel : [ {
        display : 'EmployeeID',
        name : 'name',
        width : 90,
        sortable : true,
        align : 'center'
    }, {
        display : 'Surname',
        name : 'surname',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : 'Patronymic',
        name : 'patronymic',
        width : 120,
        sortable : true,
        align : 'left'
    }, {
        display : 'email',
        name : 'email',
        width : 80,
        sortable : true,
        align : 'left',
        hide : true
    }, {
        display : 'confirmed',
        name : 'confirmed',
        width : 80,
        sortable : true,
        align : 'right'
    } ],
    buttons : [ {
        name : 'Add',
        bclass : 'add',
        onpress : Example4
    }
        ,
        {
            name : 'Edit',
            bclass : 'edit',
            onpress : Example4
        }
        ,
        {
            name : 'Delete',
            bclass : 'delete',
            onpress : Example4
        }
        ,
        {
            separator : true
        }
    ],
    searchitems : [ {
        display : 'EmployeeID',
        name : 'employeeID'
    }, {
        display : 'Name',
        name : 'name',
        isdefault : true
    } ],
    sortname : "iso",
    sortorder : "asc",
    usepager : true,
    title : 'Employees',
    useRp : true,
    rp : 15,
    showTableToggleBtn : true,
//    width : 700,
    height : 200
});

function Example4(com, grid) {
    if (com == 'Delete') {
        var conf = confirm('Delete ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    $.get('example4.php', { Delete: value.firstChild.innerText}
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $(".flexme4").flexReload();
                        });
                });
        }
    }
    else if (com == 'Edit') {
        var conf = confirm('Edit ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    // collect the data
                    var OrgEmpID = value.children[0].innerText; // in case we're changing the key
                    var EmpID = prompt("Please enter the New Employee ID",value.children[0].innerText);
                    var Name = prompt("Please enter the Employee Name",value.children[1].innerText);
                    var PrimaryLanguage = prompt("Please enter the Employee's Primary Language",value.children[2].innerText);
                    var FavoriteColor = prompt("Please enter the Employee's Favorite Color",value.children[3].innerText);
                    var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal",value.children[4].innerText);

                    // call the ajax to save the data to the session
                    $.get('example4.php',
                        { Edit: true
                            , OrgEmpID: OrgEmpID
                            , EmpID: EmpID
                            , Name: Name
                            , PrimaryLanguage: PrimaryLanguage
                            , FavoriteColor: FavoriteColor
                            , FavoritePet: FavoriteAnimal  }
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $(".flexme4").flexReload();
                        });
                });
        }
    }
    else if (com == 'Add') {
        // collect the data
        var EmpID = prompt("Please enter the Employee ID","5");
        var Name = prompt("Please enter the Employee Name","Mark");
        var PrimaryLanguage = prompt("Please enter the Employee's Primary Language","php");
        var FavoriteColor = prompt("Please enter the Employee's Favorite Color","Tan");
        var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal","Dog");

        // call the ajax to save the data to the session
        $.get('example4.php', { Add: true, EmpID: EmpID, Name: Name, PrimaryLanguage: PrimaryLanguage, FavoriteColor: FavoriteColor, FavoritePet: FavoriteAnimal  }
            , function(){
                // when ajax returns (callback), update the grid to refresh the data
                $(".flexme4").flexReload();
            });
    }
}

});