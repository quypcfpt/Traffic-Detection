var host = "http://" + location.hostname + ":" + location.port;

loadData();

function loadData() {
    google.charts.load("current", {packages: ["timeline"]}, {language: ["vi"]});
    google.charts.setOnLoadCallback(drawChart);
}

function drawChart() {
    var date = $('#date-chooser').val();
    var url = window.location.href.replace("portal", "api") + "/" + date;

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            var result = JSON.parse(res);
            $("#dataTable").find("tr:gt(0)").remove();

            document.getElementById('my-chart').innerText = "";

            var reportList = result.data;
            var container = document.getElementById('my-chart');
            var chart = new google.visualization.Timeline(container);

            if (reportList.length > 0) {
                var title = reportList[0].camera.description
                    + ", đường " + reportList[0].camera.street.name
                    + ", quận " + reportList[0].camera.street.district;

                $('#camera-title').text(title);

                loadDataTable(reportList);
                var data = buildData(reportList);

                var date = reportList[0].date;


                var dataTable = new google.visualization.DataTable();
                dataTable.addColumn({type: 'string', id: 'status'});
                dataTable.addColumn({type: 'date', id: 'Start'});
                dataTable.addColumn({type: 'date', id: 'End'})
                dataTable.addRows(data);

                var colors=getColor(reportList)

                var options = {
                    colors: colors,
                };
                var chartTitle = 'Biểu đồ mô tả tình trạng giao thông ngày ' + $('#date-chooser').val()

                $('#chart-title').text(chartTitle);
                chart.draw(dataTable, options);
            }
        }
        , error: function (e) {
            alert("Error: " + e.message);
        }
    })
}

function buildData(reportList) {
    var dataList = [];

    for (i = 0; i < reportList.length; i++) {

        if (reportList[i].endTime != null) {
            var status;
            switch (reportList[i].status) {
                case 0:
                    status = 'Bình Thường'
                    break;
                case 1:
                    status = 'Kẹt'
                    break;
                case 2:
                    status = 'Đông'
                    break;
            }
            var start = new Date(reportList[i].date + " " + reportList[i].startTime);
            var end = new Date(reportList[i].date + " " + reportList[i].endTime);

            var row = [status, start, end];
            console.log(row.toString())
            dataList.push(row);
        }
    }
    console.log(dataList)
    return dataList;
}

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
                    var start = (row || {}).startTime.toString().substr(0, 5);
                    return start;
                }
            },
            {
                data: null,
                orderable: true,
                render: function (data, type, row) {
                    var end = (row || {}).endTime;
                    if (end != null) {
                        return end.substr(0, 5);
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
                    ret = ' <button class="btn btn-warning" data-toggle="modal" data-target="#img-modal">Xem ảnh</button>'
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

$(function () {
    $("#datepicker").datepicker({
        autoclose: true,
        todayHighlight: true,
        onSelect: function () {
            console.log($(this).datepicker('getDate'));
        }
    }).datepicker('update', new Date());
});


$('#chart-btn').click(function () {
    loadData();
})

function getColor(list) {
    var count = []
    var color = []
    var x = true;
    for (var i = 0; i < list.length; i++) {
        x = true;
        for (var j = 0; j < count.length; j++) {
            if (list[i].status == count[j]) {
                x = false;
                break;
            }
        }
        if (x) {
            if (list[i].status == 0) {
                count.push(0);
                color.push("#008000");
            } else if (list[i].status == 1) {
                count.push(1);
                color.push("#e81226");
            } else if (list[i].status == 2) {
                count.push(2);
                color.push("#e9ec0c");
            }
        }
    }
    return color;
}





