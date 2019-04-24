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

    $("#datepicker-modal").datepicker({
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


// Bar Chart Example
var ctx = document.getElementById("bar-chart");
var myBarChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["0h-2h", "2h-4h", "4h-6h", "6h-8h", "10h-12h", "12h-14h",
            "14h-16h", "16h-18h", "18h-20h", "20h-22h", "22h-0h"],
        datasets: [
            {
                label: "Bình Thường",
                backgroundColor: "#4edf52",
                hoverBackgroundColor: "#2cd949",
                borderColor: "#4edf52",
                data: [1, 1, 1, 1, 2, 0, 1, 0, 0, 1, 1],
            },
            {
                label: "Đông",
                backgroundColor: "#dfd54e",
                hoverBackgroundColor: "#d9d32c",
                borderColor: "#dfd54e",
                data: [0, 0, 0, 1, 1, 2, 4, 3, 2, 1, 0],
            },
            {
                label: "Kẹt",
                backgroundColor: "#df4e4e",
                hoverBackgroundColor: "#d92d2d",
                borderColor: "#df4e4e",
                data: [0, 0, 0, 2, 1, 3, 3, 2, 1, 0, 0],
            }],
    },
    options: {
        maintainAspectRatio: false,
        layout: {
            padding: {
                left: 10,
                right: 25,
                top: 25,
                bottom: 0
            }
        },
        scales: {
            xAxes: [{
                time: {
                    unit: 'time'
                },
                gridLines: {
                    display: false,
                    drawBorder: false
                },
                ticks: {
                    maxTicksLimit: 6
                },
                maxBarThickness: 25,
            }],
            yAxes: [{
                ticks: {
                    min: 0,
                    max: 10,
                    maxTicksLimit: 5,
                    padding: 10,
                },
                gridLines: {
                    color: "rgb(234, 236, 244)",
                    zeroLineColor: "rgb(234, 236, 244)",
                    drawBorder: false,
                    borderDash: [2],
                    zeroLineBorderDash: [2]
                }
            }],
        },
        legend: {
            display: false
        },
        tooltips: {
            titleMarginBottom: 10,
            titleFontColor: '#6e707e',
            titleFontSize: 14,
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: '#dddfeb',
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
        },
    }
});

