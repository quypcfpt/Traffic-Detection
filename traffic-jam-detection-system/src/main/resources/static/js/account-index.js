var host = "http://" + location.hostname + ":" + location.port;

function onLoadAccountView(){
    var url = host + "/api/account"
        + "?page=0"

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var result = JSON.parse(res);
            $("#dataTable").find("tr:gt(0)").remove();

            var accountList = result.data.accountList;

            if (accountList != null) {
                loadDataTable(accountList)
            }

        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
}

$(onLoadAccountView());

$('#edit-btn').click(function () {

    var accountModel = {
        id: $('#edtId').val(),
        username: $('#edtUsername').val(),
        name: $('#edtName').val(),
        roleId: $('#edtRole').val(),
    }

    var accountModelString = JSON.stringify(accountModel);
    var formData = new FormData();
    formData.append("accountModelString", accountModelString);

    $.ajax({
        type: "PUT",
        url: host + "/api/account/role",
        dataType: "json",
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#edit-modal').modal('toggle');
            onLoadAccountView();
        },
        error: function (res) {
            alert(res.message);
        }
    });
});

function loadDataTable(accountList) {
    var table = $('#dataTable').DataTable({
        destroy: true,
        jQueryUI: true,
        autoWidth: false,
        data: accountList,
        searching: true, paging: true, info: false,
        columns: [
            {data: "id"},
            {data: "username"},
            {data: "name"},
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var role = (row || {}).roleId;
                    if (role == 1) {
                        ret = '<span class="label">Admin</span>';
                    } else  if(role == 2){
                        ret = '<span class="label">User</span>';
                    }
                    return ret;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    var id = (row || {}).id;
                    ret = ' <button class="btn btn-warning" data-toggle="modal" data-target="#edit-modal">Edit Info</button>'
                    return ret;
                }
            }],
        responsive: true,
    })
};

$('#dataTable').on('click', '.btn', function () {
    var curRow = $(this).closest('tr');
    var data = $('#dataTable').DataTable().row(curRow).data();

    var id = data["id"];
    var username = data["username"];
    var name =data["name"];
    var role = data["roleId"];

    $('#edtId').val(id);
    $('#edtUsername').val(username);
    $('#edtName').val(name);
    if(role == 1){
        $('#edtRole').val("Admin");
    }else{role == 2}{
        $('#edtRole').val("User");
    }
    if ($('#edtRole')[0].length == 0) {
        var url = host + "/api/role";

        $.ajax({
            url: url,
            datatype: 'json',
            type: 'GET',
            success: function (res) {
                var result = JSON.parse(res);

                var roleList = result.data.list;

                if (roleList != null) {
                    for (i = 0; i < roleList.length; i++) {
                        if(roleList[i].id == 1){
                            $('#edtRole').append("<option value='" + roleList[i].id + "'>Admin</option>");
                        }else if (roleList[i].id == 2){
                            $('#edtRole').append("<option value='" + roleList[i].id + "'>User</option>");
                        }
                    }
                    $('#edtRole').val(role);
                }
            }, error: function (e) {
                alert("Error: " + e.message);
            }
        })
    }
    $('#edtRole').val(role);
})

$('#logout-btn').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: host + "/portal/logout",
        type: "GET"
    })
    window.location.href = "/portal/login";
})