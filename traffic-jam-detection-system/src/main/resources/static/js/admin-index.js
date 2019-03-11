$('#show-camera-btn').click(function () {
    var host = "http://" + location.hostname + ":" + location.port;
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
            cameraListPage = result.data.currentPage;
            cameraListTotalPage = result.data.totalPage;

            if (cameraList != null) {
                loadDataTable(cameraList)
            }

        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
});

$('#add-camera-btn').click(function () {

    if ($('#txtStreet')[0].length==0) {
        var host = "http://" + location.hostname + ":" + location.port;
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

    var cameraModel = {
        description: $('#txtDescription').val(),
        street: $('#txtStreet').val(),
        order: $('#txtOrder').val()
    }
})


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
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    if (isActive == true) {
                        ret = '<input type="checkbox" checked data-toggle="toggle" data-on="Active" data-off="Deactive" data-onstyle="success" data-offstyle="danger">';
                    } else {
                        ret = '<input type="checkbox" data-toggle="toggle" data-on="Active" data-off="Deactive" data-onstyle="success" data-offstyle="danger">';
                    }
                    return ret;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                    ret = ' <button class="btn btn-warning">Edit Info</button>'
                    return ret;
                }
            }],

        responsive: true
    })
}