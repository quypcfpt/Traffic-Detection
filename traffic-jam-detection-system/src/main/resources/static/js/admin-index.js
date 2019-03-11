var host = "http://" + location.hostname + ":" + location.port;

$('#show-camera-btn').click(function () {

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
});

$('#add-camera-btn').click(function () {

    if ($('#txtStreet')[0].length == 0) {
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
        position: $('#txtLongitude').val() + ", " + $('#txtLatitude').val(),
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
            alert(res.message);
        },
        error: function (res) {
            alert(res.message);
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
            {data: "position"},
            {data: "street.name"},
            {data: "order"},
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    if (isActive == true) {
                        ret = '<span class="label text-secondary">Active</span>';
                    } else {
                        ret = '<span class="label text-danger">Deactive</span>';
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
            },
            {data: "street.id", visible: false, "searchable": false}],
        responsive: true,
    })
};

$('#dataTable').on('click', '.btn', function () {
    var curRow = $(this).closest('tr');
    var data = $('#dataTable').DataTable().row(curRow).data();

    var id = data["id"];
    var description =data["description"];
    var position = data["position"];
    var spit = position.split(", ");
    var order = data["order"];

    var streetId = data["street.id"];

    $('#edtId').val(id);
    $('#edtDescription').val(description);
    $('#edtLongitude').val(spit[0]);
    $('#edtLatitude').val(spit[1]);
    $('#edtOrder').val(order);

    alert(streetId);

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
