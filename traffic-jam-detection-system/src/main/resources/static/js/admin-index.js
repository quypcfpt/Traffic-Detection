var host = "http://" + location.hostname + ":" + location.port;

$(function () {
    showCameraTable();
})

function showCameraTable() {
    var url = host + "/api/camera"
        + "?page=0"

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var result = JSON.parse(res);
            $("#dataTable").find("tr:gt(0)").remove();

            var cameraList = result.data.cameraList;

            if (cameraList != null) {
                loadDataTable(cameraList)
            }

        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
};

$('#add-camera-btn').click(function () {

    if ($('#txtStreet')[0].length == 0) {
        var url = host + "/api/street"
            + "?page=0";

        $.ajax({
            url: url,
            datatype: 'JSON',
            type: 'GET',
            success: function (res) {
                var result = JSON.parse(res);

                var streetList = result.data.streetList;

                if (streetList != null) {
                    for (i = 0; i < streetList.length; i++) {
                        $('#txtStreet').append("<option value='" + streetList[i].id + "'>"
                            + streetList[i].name + "-" + streetList[i].district
                            + "</option>");
                    }
                }
            }, error: function (e) {
                alert("Error: " + e.message);
            }
        })
    }
});

$('#save-btn').click(function () {

    var street = {
        id: $('#txtStreet').val(),
    }

    var cameraModel = {
        description: $('#txtDescription').val(),
        street: street,
        position: $('#txtLongitude').val() + "," + $('#txtLatitude').val(),
        order: $('#txtOrder').val()
    }

    var cameraModelString = JSON.stringify(cameraModel);
    var formData = new FormData();
    formData.append("cameraModelString", cameraModelString);

    $.ajax({
        type: "POST",
        url: host + "/api/camera",
        dataType: "json",
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#create-modal').modal('toggle');
            showCameraTable();
            $.bootstrapGrowl('Create Success.', {
                type: 'success',
                delay: 2000,
            });
        },
        error: function (res) {
            $('#edit-modal').modal('toggle');
            $.bootstrapGrowl('You Got Error', {
                type: 'danger',
                delay: 2000,
            });
        }
    });
});

$('#edit-btn').click(function () {

    var street = {
        id: $('#edtStreet').val(),
    }

    var cameraModel = {
        id: $('#edtId').val(),
        description: $('#edtDescription').val(),
        street: street,
        position: $('#edtLongitude').val() + ", " + $('#edtLatitude').val(),
        order: $('#edtOrder').val(),
        isActive: $('#edtActive').prop('checked')
    }

    var cameraModelString = JSON.stringify(cameraModel);
    var formData = new FormData();
    formData.append("cameraModelString", cameraModelString);

    $.ajax({
        type: "PUT",
        url: host + "/api/camera",
        dataType: "json",
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#edit-modal').modal('toggle');
            showCameraTable();
            $.bootstrapGrowl('Edit Success.', {
                type: 'success',
                delay: 2000,
            });
        },
        error: function (res) {
            $('#edit-modal').modal('toggle');
            $.bootstrapGrowl('You Got Error', {
                type: 'danger',
                delay: 2000,
            });
        }
    });
});

function loadDataTable(cameraList) {
    var table = $('#dataTable').DataTable({
        destroy: true,
        jQueryUI: true,
        autoWidth: false,
        data: cameraList,
        searching: true, paging: true, info: false,
        columns: [
            {data: "id"},
            {data: "description"},
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var position = (row || {}).position;
                    var id = (row || {}).id;
                    ret = ' <a href="#" class="a-tag" data-toggle="modal" data-target="#map-modal">' + position + '</a>'
                    return ret;
                }
            },
            {data: "street.name"},
            {data: "order"},
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    if (isActive == true) {
                        ret = '<span class="label text-success">Đang hoạt động</span>';
                    } else {
                        ret = '<span class="label text-danger">Ngừng hoạt động</span>';
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
                    ret = ' <button class="btn btn-warning" data-toggle="modal" data-target="#edit-modal">Chỉnh sửa</button>'
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
    var description = data["description"];
    var position = data["position"];
    var spit = position.split(", ");
    var order = data["order"];

    var street = data["street"];
    var streetId = street.id;

    var active = data["isActive"];
    if (active == true) {
        $('#edtActive').prop("checked", true).change();
    } else {
        $('#edtActive').prop("checked", false).change();
    }

    $('#edtId').val(id);
    $('#edtDescription').val(description);
    $('#edtLongitude').val(spit[1]);
    $('#edtLatitude').val(spit[0]);
    $('#edtOrder').val(order);


    if ($('#edtStreet')[0].length == 0) {
        var url = host + "/api/street"
            + "?page=0";

        $.ajax({
            url: url,
            datatype: 'json',
            type: 'GET',
            success: function (res) {
                var result = JSON.parse(res);

                var streetList = result.data.streetList;

                if (streetList != null) {
                    for (i = 0; i < streetList.length; i++) {
                        $('#edtStreet').append("<option value='" + streetList[i].id + "'>"
                            + streetList[i].name + "-" + streetList[i].district
                            + "</option>");
                    }
                    $('#edtStreet').val(streetId);
                }
            }, error: function (e) {
                alert("Error: " + e.message);
            }
        })
    }
    $('#edtStreet').val(streetId);

})

$('#logout-btn').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: host + "/portal/logout",
        type: "GET"

    })
    window.location.href = "/portal/login";
})