$('#show-camera-btn').click(function () {
    var url = "http://localhost:8080/api/camera"
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
                    if (isActive == 1) {
                        ret = '<input type="checkbox" data-toggle="toggle" data-on="Active" data-off="Deactive" checked>';
                    } else {
                        ret = '<input type="checkbox" data-toggle="toggle" data-on="Active" data-off="Deactive">';
                    }
                    return ret;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).isActive;
                   ret=' <button class="btn btn-warning">Edit Info</button>'
                    return ret;
                }
            }],

        responsive: true
    })
}