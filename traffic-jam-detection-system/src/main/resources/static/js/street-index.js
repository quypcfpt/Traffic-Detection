var host = "http://" + location.hostname + ":" + location.port;

function onLoadStreetView() {
    var url = host + "/api/street"
        + "?page=0"

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var result = JSON.parse(res);
            $("#dataTable").find("tr:gt(0)").remove();

            var streetList = result.data.streetList;

            if (streetList != null) {
                loadDataTable(streetList)
            }

        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
}

$(onLoadStreetView());

$('#save-btn').click(function () {

    var streetModel = {
        name: $('#txtName').val(),
        district: $('#txtDistrict').val(),
        city: $('#txtCity').val(),
    }

    var streetModelString = JSON.stringify(streetModel);
    var formData = new FormData();
    formData.append("streetModelString", streetModelString);

    $.ajax({
        type: "POST",
        url: host + "/api/street",
        dataType: "json",
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#create-modal').modal('toggle');
            $.bootstrapGrowl('Add Success.',{
                type: 'success',
                delay: 2000,
            });
            onLoadStreetView();
            $('#txtName').val('');
            $('#txtDistrict').val('');
            $('#txtCity').val('')
        },
        error: function (res) {
            $('#create-modal').modal('toggle');
            $.bootstrapGrowl('You Got Error',{
                type: 'danger',
                delay: 2000,
            });
        }
    });
});

$('#edit-btn').click(function () {

    var streetModel = {
        id: $('#edtId').val(),
        name: $('#edtName').val(),
        district: $('#edtDistrict').val(),
        city: $('#edtCity').val(),
        isActive: $('#edtActive').prop('checked')
    }

    var streetModelString = JSON.stringify(streetModel);
    var formData = new FormData();
    formData.append("streetModelString", streetModelString);

    $.ajax({
        type: "PUT",
        url: host + "/api/street",
        dataType: "json",
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#edit-modal').modal('toggle');
            $.bootstrapGrowl('Edit Success.',{
                type: 'success',
                delay: 2000,
            });
            onLoadStreetView();
        },
        error: function (res) {
            $('#edit-modal').modal('toggle');
            $.bootstrapGrowl('You Got Error',{
                type: 'danger',
                delay: 2000,
            });
        }
    });
});

function loadDataTable(streetList) {
    var table = $('#dataTable').DataTable({
        destroy: true,
        jQueryUI: true,
        autoWidth: false,
        data: streetList,
        searching: true, paging: true, info: false,
        columns: [
            {data: "id"},
            {data: "name"},
            {data: "district"},
            {data: "city"},
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    if (isActive == true) {
                        ret = '<span class="label text-success">Active</span>';
                    } else {
                        ret = '<span class="label text-danger">Deactive</span>';
                    }
                    return ret;
                }
            },
            {
                data: null,
                orderable: false,
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
    var name = data["name"];
    var district =data["district"];
    var city = data["city"];

    var active=data["isActive"];
    if (active==true){
        $('#edtActive').prop("checked", true).change();
    }else{
        $('#edtActive').prop("checked", false).change();
    }

    $('#edtId').val(id);
    $('#edtName').val(name);
    $('#edtDistrict').val(district);
    $('#edtCity').val(city);

})

$('#logout-btn').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: host + "/portal/logout",
        type: "GET"

    })
    window.location.href = "/portal/login";
})