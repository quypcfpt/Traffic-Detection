var host = "http://" + location.hostname + ":" + location.port;
// $(function () {
//     $('#login-error').hide();
// })

$('#login-btn').click(function (e) {
    e.preventDefault();
    var url = host + "/api/account/admin"
    var accountModel = {
        username: $('#txtUsername').val(),
        password: $('#txtPassword').val()
    }

    var account = JSON.stringify(accountModel);
    var formData = new FormData();
    formData.append("accountModel", account);
    // window.location.href = "/portal/test";

    $.ajax({
        type: "POST",
        url: url,
        data: formData,
        datatype: 'json',
        contentType: false,
        processData: false,
        success: function (res) {
            var result = JSON.parse(res);
            if(result.data === "success"){
                window.location.href = "/portal/camera";
            }else if(result.data === "failed"){
                $('#login-error').css('visibility', 'visible');
                $('#txtPassword').val("");
                console.log(res);
                // alert(("Invalid username or password"));
            }
        }
    })
})