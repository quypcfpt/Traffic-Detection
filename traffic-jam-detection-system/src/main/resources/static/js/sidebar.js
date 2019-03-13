var host = "http://" + location.hostname + ":" + location.port;

$('#show-camera-btn').click(function () {
    var url = host + "/portal/test";

    $.ajax({
        url: url,
        datatype: 'json',
        type: 'GET',
        success: function (res) {
            alert("ok")
        }, error: function (e) {
            alert("Error: " + e.message);
        }
    })
})