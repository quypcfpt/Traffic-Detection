var map;
var markers = [];

function initMap() {

    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 10.820439, lng: 106.630633},
        zoom: 12
    });

    google.maps.event.addListener(map, 'click', function (event) {
        placeMarker(event.latLng);
        $('#txtLatitude').val(event.latLng.lat);
        $('#txtLongitude').val(event.latLng.lng);
    });
}

$('#search-btn').click(function () {
    var lat = $('#txtLatitude').val();
    var lng = $('#txtLongitude').val();


    var latLng = new google.maps.LatLng(lat, lng); //Makes a latlng
    map.panTo(latLng); //Make map global
    map.setZoom(16);
    placeMarker(latLng);
})

function placeMarker(location) {
    clearMarkers();
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
    markers.push(marker);
}

function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

$('#dataTable').on('click', '.a-tag', function () {
    var curRow = $(this).closest('tr');
    var data = $('#dataTable').DataTable().row(curRow).data();

    var description = data["description"];
    var position = data["position"];

    $('#map-title').html(description);
    var lat = position.split(',')[0];
    var lng = position.split(',')[1];

    var latLng = new google.maps.LatLng(lat, lng); //Makes a latlng
    var modalMap = new google.maps.Map(document.getElementById('modal-map'), {
        center: latLng,
        zoom: 16
    });

    var marker = new google.maps.Marker({
        position: latLng,
        title: position
    });
    marker.setMap(modalMap);
})
