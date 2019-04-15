package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.PositionModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.StreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.DirectionsJSONParser;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.HttpUtils;

import static trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.SearchRouteActitvity.REQUEST_ID_ACCESS_COURSE_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationSource.OnLocationChangedListener,GoogleMap.OnMyLocationChangeListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean MY_REQUEST_CHECK = false;
    private static final float ZOOMVALUE = 16f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSON_REQUEST_CODE = 1234;

    //
    ApiInterface apiInterface;
    private static CameraAdapter adapter;
    private RecyclerView recyclerView;
    private TextView labelTextView , textErr;
    private ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;
    private ImageButton btnBack;
    private List<CameraModel> cameraModels=null;
    String strAdd = "";
    String oldStreet = "";
    String currenPostion = "";
    String theWayOfDevice="";
    int count = 0;
    LatLng newPostion;
    int latiWayStatus=0;
    int longtiWayStatus = 0;
    GoogleMap googleMap;
    private ArrayList<LatLng> searchPoints;
    private ArrayList<CameraModel> searchCameras;
    private String oriStr = "";
    private String desStr = "";
    private ArrayList<LatLng> onRoutePoints;
    private Bundle bundle;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mLayoutManager = new LinearLayoutManager(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        bundle = getIntent().getExtras();
        if(bundle != null){
            initMap();
            searchPoints = bundle.getParcelableArrayList("POINTS");
            searchCameras = (ArrayList<CameraModel>) bundle.getSerializable("CAMERAS");
            oriStr = bundle.getString("ORI");
            desStr = bundle.getString("DES");
        }else{
            getLocationPermission();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

    }

    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                MY_REQUEST_CHECK = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSON_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSON_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MY_REQUEST_CHECK = false;
        switch (requestCode) {
            case LOCATION_PERMISSON_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            MY_REQUEST_CHECK = true;
                            return;
                        }
                    }
                    MY_REQUEST_CHECK = true;
                    //initalize map
                    initMap();
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (MY_REQUEST_CHECK) {
            //Rung demo Check device location
            getDeviceLocation();

            //Show map and draw route
//            MarkerOptions place1 = new MarkerOptions().position(new LatLng(10.838751, 106.648976));
//            MarkerOptions place2 = new MarkerOptions().position(new LatLng(10.852706, 106.629692));
//            getDirectionURL(place1.getPosition(),place2.getPosition());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//            mMap.setOnMyLocationChangeListener(this);
        }else{
            drawMapFromBookmark();
        }
    }

    //get Location
    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (MY_REQUEST_CHECK) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            testGPS();
                        } else {
                            Toast.makeText(MapsActivity.this, "unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("DEVICE LOCATION ERR:", e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private String getStreetNameAtLocation(LatLng location) {
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 3);
//            List<Address> addresses = geocoder.getFromLocation(10.852706, 106.629692, 3);
            if (addresses != null) {
                Address add = addresses.get(0);
                String subthorough = "";
                String thorough = "";
                String feature = "";
                String subadmin = "";
                for (int i = 0; i < addresses.size(); i++) {
                    subthorough = addresses.get(i).getSubThoroughfare();
                    thorough = addresses.get(i).getThoroughfare();
                    feature = addresses.get(i).getFeatureName();
                    subadmin = addresses.get(i).getSubAdminArea();
                    if(thorough.contains("Đường")){
                        String [] arr = thorough.split(" ", 2);

                        strAdd = arr[1];
                    }else{
                        strAdd =thorough.trim().toString();
                    }

                     if (!strAdd.equals("")){
                         break;
                     }

                }
                if (strAdd.equals("")) {
                    strAdd = addresses.get(0).getAddressLine(0);
                }

                Log.d("STREET ADDRESS", strAdd);
            } else {
                Log.d("street", "null address");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Toast.makeText(this, "Located your position", Toast.LENGTH_SHORT).show();

        return  strAdd;
    }
    private void getCamearaOnStreet(String streetName){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<List<CameraModel>>> responseCall = apiInterface.loadCamerasByStreetName(strAdd);
        responseCall.enqueue(new Callback<Response<List<CameraModel>>>() {
            @Override
            public void onResponse(Call<Response<List<CameraModel>>> call, retrofit2.Response<Response<List<CameraModel>>> response) {
                int busyStatusCount=0;
                Response<List<CameraModel>> res = response.body();

                final List<CameraModel> multiCameraModel = res.getData();
                cameraModels = res.getData();

                    final List<CameraModel> cameraList =  new ArrayList<>();
                    for(CameraModel item  : cameraModels) {
                        LatLng position = getCameraLocation(item.getPosition());
                        //compare to find the device way
                        double resultLatitude =   position.latitude - newPostion.latitude;
                        double resultLontitude =  position.longitude -newPostion.longitude ;
                        if (latiWayStatus == 1 && longtiWayStatus == 1 && resultLatitude > 0 && resultLontitude > 0) {
                            float distance = calculationByDistance(newPostion, position); // calculate distance from current device location wo camera on street
                            cameraList.add(new CameraModel(item.getId(), item.getDescription(), item.getPosition(), item.getResource(), item.getObserverStatus(),
                                    item.getCamOrder(), item.getStreet(), item.getImgUrl(), item.getTime(), distance)
                            );
                        } else if (latiWayStatus == 1 && longtiWayStatus == 0 && resultLatitude > 0 && resultLontitude < 0) {
                            float distance = calculationByDistance(newPostion, position); // calculate distance from current device location wo camera on street
                            cameraList.add(new CameraModel(item.getId(), item.getDescription(), item.getPosition(), item.getResource(), item.getObserverStatus(),
                                    item.getCamOrder(), item.getStreet(), item.getImgUrl(), item.getTime(), distance)
                            );
                        } else if (latiWayStatus == 0 && longtiWayStatus == 1 && resultLatitude < 0 && resultLontitude > 0) {
                            float distance = calculationByDistance(newPostion, position); // calculate distance from current device location wo camera on street
                            cameraList.add(new CameraModel(item.getId(), item.getDescription(), item.getPosition(), item.getResource(), item.getObserverStatus(),
                                    item.getCamOrder(), item.getStreet(), item.getImgUrl(), item.getTime(), distance)
                            );
                        } else if (latiWayStatus == 0 && longtiWayStatus == 0 && resultLatitude < 0 && resultLontitude < 0) {
                            float distance = calculationByDistance(newPostion, position); // calculate distance from current device location wo camera on street
                            cameraList.add(new CameraModel(item.getId(), item.getDescription(), item.getPosition(), item.getResource(), item.getObserverStatus(),
                                    item.getCamOrder(), item.getStreet(), item.getImgUrl(), item.getTime(), distance)
                            );
                        }
                    }

                if(!multiCameraModel.isEmpty()) {
                    for (CameraModel camera : cameraList) {
                        if (camera.getObserverStatus() == 1) {
                            busyStatusCount += 1;
                        }
                    }
                    if(!cameraList.isEmpty()){
                        Log.d("Camera : " , cameraList.toString());
                        Collections.sort(cameraList);
                        Log.d("CameraSort : " , cameraList.toString());
                        RequestParams params = getParams(newPostion.latitude+","+newPostion.longitude,cameraList.get(cameraList.size()-1).getPosition());
                        getRoutePoints(params);
                        for (CameraModel x : cameraList) {
                            LatLng locationCamera = getCameraLocation(x.getPosition());
                            MarkerOptions marker = null;
                            if (x.getObserverStatus() == 0) {
                                marker = new MarkerOptions().position(new LatLng(locationCamera.latitude, locationCamera.longitude)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_green));
                            } else if (x.getObserverStatus() == 1) {
                                marker = new MarkerOptions().position(new LatLng(locationCamera.latitude, locationCamera.longitude)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_red));
                            } else {
                                marker = new MarkerOptions().position(new LatLng(locationCamera.latitude, locationCamera.longitude)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_yellow));
                            }
                            mMap.addMarker(marker);


                        }
                    }
//                        if (busyStatusCount > 0 && !strAdd.equals(oldStreet)) {
//                            oldStreet = strAdd;
//                            final int finalBusyStatusCount = busyStatusCount;
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                                    builder.setCancelable(false);
//                                    builder.setTitle("Road Status");
//                                    builder.setMessage("The Road " + strAdd + " has " + finalBusyStatusCount + " locations is busy .Click Show to get more infomation");
//                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                                    builder.setPositiveButton("Show", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(MapsActivity.this, ListCameraActivity.class);
//                                            Bundle bundle = new Bundle();
//                                            String listCamJsonObj = new Gson().toJson(cameraList);
//                                            bundle.putString("LIST", listCamJsonObj);
//                                            bundle.putString("STREET_NAME", cameraModels.get(0).getStreet().getName());
//                                            bundle.putString("STREET_ID", cameraModels.get(0).getStreet().getId() + "");
//                                            intent.putExtras(bundle);
//                                            startActivity(intent);
//                                        }
//                                    });
//                                    final AlertDialog alert = builder.create();
//                                    alert.show();
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (alert.isShowing()) {
//                                                alert.dismiss();
//                                            }
//                                        }
//                                    }, 1000 * 3);//show message box
//                                }
//                            }, 1000 / 2);
//                        }
                }
            }
            @Override
            public void onFailure(Call<Response<List<CameraModel>>> call, Throwable t) {
//                Log.e("ERROR",t.getMessage());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {



    }
    public void testGPS() {
        final List<PositionModel> postion = new ArrayList<>();
//        postion.add(new PositionModel(10.852706, 106.629692));
//        postion.add(new PositionModel(10.852358, 106.627646));
//        postion.add(new PositionModel(10.852291, 106.626731));
        postion.add(new PositionModel(10.850984, 106.628128));
        postion.add(new PositionModel(10.850233, 106.631208));
        postion.add(new PositionModel(10.850004, 106.631572));
        postion.add(new PositionModel(10.849502, 106.632250));
       //position change to QT road
        postion.add(new PositionModel(10.842553, 106.642668));
        postion.add(new PositionModel(10.841229, 106.644644));
//        postion.add(new PositionModel(10.840535, 106.645538));
//        postion.add(new PositionModel(10.839451, 106.646859));
//        postion.add(new PositionModel(10.838978, 106.648116));
//        postion.add(new PositionModel(10.838717, 106.649182));
//        postion.add(new PositionModel(10.838420, 106.650428));
//        postion.add(new PositionModel(10.838154, 106.651474));

    // position move backward
//        postion.add(new PositionModel(10.838717, 106.649182));
//        postion.add(new PositionModel(10.838978, 106.648116));
//        postion.add(new PositionModel(10.839354, 106.646986));
//        postion.add(new PositionModel(10.840535, 106.645538));
//        postion.add(new PositionModel(10.841229, 106.644644));
//        postion.add(new PositionModel(10.842553, 106.642668));

        //Test for anthor street
        postion.add(new PositionModel(10.843921, 106.639865));
        postion.add(new PositionModel(10.843005, 106.639326));
        postion.add(new PositionModel(10.841694, 106.638478));
        postion.add(new PositionModel(10.840671, 106.637792));
        postion.add(new PositionModel(10.838750, 106.636894));
        postion.add(new PositionModel(10.836207, 106.635722));
        //Test for anthor street backward
//        postion.add(new PositionModel(10.836207, 106.635722));
//        postion.add(new PositionModel(10.838750, 106.636894));
//        postion.add(new PositionModel(10.840671, 106.637792));
//        postion.add(new PositionModel(10.841694, 106.638478));
//        postion.add(new PositionModel(10.843005, 106.639326));
//        postion.add(new PositionModel(10.843921, 106.639865));



//        LatLng newPostion = new LatLng(10.849294, 106.632329);
//        LatLng lastPosition = new LatLng(10.841762, 106.643714);
//        moveCamera(new LatLng(postion.get(6).getLatitude(), postion.get(6).getLongtitude()), ZOOMVALUE);
//        String streetName = getStreetNameAtLocation(newPostion);
//        Log.d("DEMOGPS", "Street Name :" + streetName + " latitude : " + postion.get(count).getLatitude());
//        if (!streetName.equals("")) {
//            getCamearaOnStreet(streetName);
//        }
//        calculationByDistance(newPostion,lastPosition);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int counter = 0;
            int delay = 1000*10;
            @Override
            public void run() {
                boolean wasPlacedInQue = false;
                if(counter < postion.size()){
                    mMap.clear();
                    //get the way of device
                    if(counter>0) {
                        double latiWay = postion.get(counter).getLatitude() - postion.get(counter-1).getLatitude();
                        double longtiWay = postion.get(counter).getLongtitude() - postion.get(counter-1).getLongtitude();
                        if(latiWay > 0){
                            latiWayStatus = 1;
                        }else{
                            latiWayStatus = 0;
                        }
                        if(longtiWay >0){
                            longtiWayStatus = 1;
                        }else{
                            longtiWayStatus = 0;
                        }
                    }
                    //************
                    newPostion = new LatLng(postion.get(counter).getLatitude(), postion.get(counter).getLongtitude());
                    moveCamera(new LatLng(postion.get(counter).getLatitude(), postion.get(counter).getLongtitude()), ZOOMVALUE);
                    mMap.addMarker(new MarkerOptions().position(newPostion).title("You're here")).showInfoWindow();
                    String streetName = getStreetNameAtLocation(newPostion);
                    //--- code here
                    //--get list camera with streetName
                    //--get list camera on the way
                    //--draw route on map
                    Log.d("DEMOGPS", "Street Name :" + streetName + " latitude : " + postion.get(count).getLatitude());
                    if (!streetName.equals("")) {
                        currenPostion=newPostion.latitude+","+newPostion.longitude;
                        getCamearaOnStreet(streetName);
                    }
                    handler.postDelayed(this, delay);
                    counter++;
                }else{
                    counter = 5;
//                    handler.postDelayed(this, delay);
                }

                Toast.makeText(MapsActivity.this, "Count " + counter, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d("DEMOGPS", "Street Name :" + location.getLatitude() + " latitude : " + location.getLongitude());
    }
    private RequestParams getParams(String ori, String des) {
        RequestParams params = new RequestParams();
        params.add("origin", ori);
        params.add("destination", des);
        params.add("key", getResources().getString(R.string.google_maps_key));
        return params;
    }

    private float calculationByDistance(LatLng startP,LatLng endP){
       float[] result =new float[1];
       Location.distanceBetween(startP.latitude,startP.longitude,endP.latitude,endP.longitude,result);
        Log.d("DISTANCE : ",result[0]+"");
        return result[0];
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MapsActivity.this.finish();
    }
        private LatLng getCameraLocation(String location){
            String[] parts = location.split(",");
            LatLng result = new LatLng(Double.parseDouble(parts[0]),Double.parseDouble(parts[1]));
            return result;
        }


    public void getRoutePoints(RequestParams params) {
        HttpUtils.getByUrl("https://maps.googleapis.com/maps/api/directions/json", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<List<HashMap<String, String>>> routes = null;
                try {
                    DirectionsJSONParser parser = new DirectionsJSONParser();
                    routes = parser.parse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (routes != null) {
                    ArrayList<LatLng> points = new ArrayList<>();
                    // Traversing through all the routes
                    for (int i = 0; i < routes.size(); i++) {

                        // Fetching i-th route
                        List<HashMap<String, String>> path = routes.get(i);

                        // Fetching all the points in i-th route
                        for (int j = 0; j < path.size(); j++) {
                            HashMap<String, String> point = path.get(j);
                            double lat = Double.parseDouble(point.get("lat"));
                            double lng = Double.parseDouble(point.get("lng"));
                            LatLng position = new LatLng(lat, lng);
//                            position.
                            points.add(position);
                        }

                    }
                    if(points.isEmpty()){
                        Toast.makeText(MapsActivity.this, "Cannot get RoutePoints", Toast.LENGTH_SHORT).show();
                    } else {
                        PolylineOptions lineOptions = new PolylineOptions();
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng x : points) {
                            builder.include(x);
                        }
                        lineOptions.addAll(points);
                        lineOptions.width(15);
                        lineOptions.color(Color.BLUE);
                        Polyline directionPolyline = mMap.addPolyline(lineOptions);
                        directionPolyline.setStartCap(new RoundCap());
                        directionPolyline.setEndCap(new RoundCap());
                        directionPolyline.setJointType(JointType.ROUND);
                        directionPolyline.setPattern(null);
                        //bound route
                        LatLngBounds bounds = builder.build();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", throwable.getMessage());
                Toast.makeText(getApplicationContext(), "LỖI: Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void drawMapFromBookmark(){
        mMap.clear();
        PolylineOptions lineOptions = new PolylineOptions();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(LatLng x : searchPoints){
            builder.include(x);
        }
        lineOptions.addAll(searchPoints);
        lineOptions.width(15);
        lineOptions.color(Color.BLUE);
        Polyline directionPolyline = mMap.addPolyline(lineOptions);
        directionPolyline.setStartCap(new RoundCap());
        directionPolyline.setEndCap(new RoundCap());
        directionPolyline.setJointType(JointType.ROUND);
        directionPolyline.setPattern(null);
        //bound route
        LatLngBounds bounds = builder.build();
        mMap.addMarker(new MarkerOptions().position(searchPoints.get(0)).title(oriStr)).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(searchPoints.get(searchPoints.size() - 1)).title(desStr));
        for(CameraModel x : searchCameras){
            Double lat = Double.parseDouble(x.getPosition().split(",")[0]);
            Double longi = Double.parseDouble(x.getPosition().split(",")[1]);
            MarkerOptions marker = null;
            if(x.getObserverStatus() == 0) {
                marker = new MarkerOptions().position(new LatLng(lat, longi)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_green));
            }else if(x.getObserverStatus() == 1) {
                marker = new MarkerOptions().position(new LatLng(lat, longi)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_red));
            }else{
                marker = new MarkerOptions().position(new LatLng(lat, longi)).title(x.getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.camera_marker_yellow));
            }
            mMap.addMarker(marker);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = getReceiver();
        LocalBroadcastManager.getInstance(MapsActivity.this).registerReceiver((receiver),
                new IntentFilter("Camera")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(MapsActivity.this).unregisterReceiver((receiver));
    }

    private BroadcastReceiver getReceiver(){
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    int cameraId = intent.getIntExtra("CAMERA_ID", -1);
                    int status = intent.getIntExtra("STATUS", -1);
                    Log.d("BROADCAST","ON");
                    Log.d("BROADCAST CAMERA",cameraId + "");
                    Log.d("BROADCAST STATUS",status + "");
                    for(CameraModel x : searchCameras){
                        if(x.getId() == cameraId){
                            x.setObserverStatus(status);
                            drawMapFromBookmark();
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        return receiver;
    }
}
