var host = "http://" + location.hostname + ":" + location.port;

function onLoadReportView(date) {
    var url = window.location.href.replace("portal", "api") + "/" + date;

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var result = JSON.parse(res);
            $("#dataTable").find("tr:gt(0)").remove();

            var reportList = result.data;

            if (reportList != null) {
                loadDataTable(reportList)
            }

        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
}

$(onLoadReportView(getCurDate()));

function loadDataTable(reportList) {
    console.log("load");
    var table = $('#dataTable').DataTable({
        destroy: true,
        jQueryUI: true,
        autoWidth: false,
        data: reportList,
        searching: true, paging: true, info: false,
        columns: [
            {
                data: null,
                orderable: true,
                render: function (data, type, row) {
                    var status = (row || {}).status;
                    if (status == 0) {
                        return 'Bình thường';
                    } else if (status == 1) {
                        return 'Kẹt';
                    } else {
                        return 'Đông';
                    }
                }
            },
            {
                data: null,
                orderable: true,
                render: function (data, type, row) {
                    var start = (row || {}).startTime;
                    start = start.split(" ")[1];
                    return start;
                }
            },
            {
                data: null,
                orderable: true,
                render: function (data, type, row) {
                    var end = (row || {}).endTime;
                    if (end != null) {
                        end = end.split(" ")[1];
                        return end;
                    }
                   return "Null";
                }
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, row) {
                    var ret;
                    var isActive = (row || {}).imgUrl;
                    var id = (row || {}).id;
                    ret = ' <button class="btn btn-warning" data-toggle="modal" data-target="#edit-modal">Xem ảnh</button>'
                    return ret;
                }
            }],
        responsive: true,
    })
};

$('#dataTable').on('click', '.btn', function () {
    var curRow = $(this).closest('tr');
    var data = $('#dataTable').DataTable().row(curRow).data();
    var image = data["imgUrl"];
    var imageList = image.split(", ");
    var ret = "";
    for (var i = 0; i < imageList.length; i++) {
        ret += "<img style=\"width: 235px; height: 235px; margin: 15px\" src=\"" + imageList[i] + "\" class=\"rounded\" alt=\"...\">";
        if (ret == 2) {
            ret += "<br>";
        }
    }
    document.getElementById("image-list").innerHTML = ret;
})

$('#logout-btn').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: host + "/portal/logout",
        type: "GET"

    })
    window.location.href = "/portal/login";
})

function getCurDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    return dd + '-' + mm + '-' + yyyy;
}

// $(".form-control").onChange(function(){
//     console.log($('.form-control').val());
// })
$(function () {
    $("#datepicker").datepicker({
        autoclose: true,
        todayHighlight: true,
        onSelect: function () {
            console.log($(this).datepicker('getDate'));
        }
    }).datepicker('update', new Date());
});


$("#datepicker").on("changeDate", function () {
    onLoadReportView($('#date-chooser').val());
});