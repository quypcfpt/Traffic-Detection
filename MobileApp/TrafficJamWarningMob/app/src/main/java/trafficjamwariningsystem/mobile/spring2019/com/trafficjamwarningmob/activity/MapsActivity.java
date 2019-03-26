package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static final float ZOOMVALUE = 15f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSON_REQUEST_CODE = 1234;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private LocationRequest mLocationRequest;

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
    int count = 0;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mLayoutManager = new LinearLayoutManager(this);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        getLocationPermission();

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
//            getDeviceLocation();

            //Show map and draw route
            MarkerOptions place1 = new MarkerOptions().position(new LatLng(10.838751, 106.648976));
            MarkerOptions place2 = new MarkerOptions().position(new LatLng(10.852706, 106.629692));
            getDirectionURL(place1.getPosition(),place2.getPosition());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//            mMap.setOnMyLocationChangeListener(this);
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
//                            Log.d("SUCESS", "on found location");
//                            Location currenLocation = (Location) task.getResult();
//                            moveCamera(new LatLng(currenLocation.getLatitude(), currenLocation.getLongitude()), ZOOMVALUE);
//                            String streetName = getStreetNameAtLocation(new LatLng(currenLocation.getLatitude(), currenLocation.getLongitude()));
//
//                            if(!streetName.equals("")) {
//                                getCamearaOnStreet(streetName);
//                            }
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

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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
                    strAdd =thorough.trim().toString();
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
                if(!multiCameraModel.isEmpty()) {
                    for (CameraModel camera : multiCameraModel) {
                        if (camera.getObserverStatus() == 1) {
                            busyStatusCount += 1;
                        }
                    }
                }
                if(busyStatusCount >0 && !strAdd.equals(oldStreet)){
                    oldStreet = strAdd;
                    final int finalBusyStatusCount = busyStatusCount;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Road Status");
                            builder.setMessage("The Road "+strAdd+" has "+ finalBusyStatusCount +" locations is busy .Click Show to get more infomation");
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setPositiveButton("Show", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MapsActivity.this,ListCameraActivity.class);
                                    intent.putExtra("STREET_NAME",cameraModels.get(0).getStreet().getName());
                                    intent.putExtra("STREET_ID",cameraModels.get(0).getStreet().getId()+"");
                                    startActivity(intent);
                                }
                            });
                            final AlertDialog alert = builder.create();
                            alert.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(alert.isShowing()){
                                        alert.dismiss();
                                    }
                                }
                            },1000*5);
                        }
                    },1000*2);
                }

            }
            @Override
            public void onFailure(Call<Response<List<CameraModel>>> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {



    }
    public void testGPS() {
        final List<PositionModel> postion = new ArrayList<>();
        postion.add(new PositionModel(10.852706, 106.629692));
        postion.add(new PositionModel(10.852358, 106.627646));
        postion.add(new PositionModel(10.852291, 106.626731));
        postion.add(new PositionModel(10.851073, 106.628148));
        postion.add(new PositionModel(10.850242, 106.631050));
        postion.add(new PositionModel(10.848841, 106.633274));
        postion.add(new PositionModel(10.846822, 106.636058));
        postion.add(new PositionModel(10.841815, 106.643752));
        postion.add(new PositionModel(10.841100, 106.644942));
        postion.add(new PositionModel(10.840075, 106.646103));
        postion.add(new PositionModel(10.839264, 106.647215));
        postion.add(new PositionModel(10.838751, 106.648976));
//        LatLng newPostion = new LatLng(postion.get(6).getLatitude(), postion.get(6).getLongtitude());
//        moveCamera(new LatLng(postion.get(6).getLatitude(), postion.get(6).getLongtitude()), ZOOMVALUE);
//        String streetName = getStreetNameAtLocation(newPostion);
//        Log.d("DEMOGPS", "Street Name :" + streetName + " latitude : " + postion.get(count).getLatitude());
//        if (!streetName.equals("")) {
//            getCamearaOnStreet(streetName);
//        }
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int counter = 0;
            int delay = 5000;
            @Override
            public void run() {
                mMap.clear();
                LatLng newPostion = new LatLng(postion.get(counter).getLatitude(), postion.get(counter).getLongtitude());
                moveCamera(new LatLng(postion.get(counter).getLatitude(), postion.get(counter).getLongtitude()), ZOOMVALUE);
                MarkerOptions options = new MarkerOptions().position(new LatLng(postion.get(counter).getLatitude(), postion.get(counter).getLongtitude())).title("Your Here");
                mMap.addMarker(options);
                String streetName = getStreetNameAtLocation(newPostion);
                Log.d("DEMOGPS", "Street Name :" + streetName + " latitude : " + postion.get(count).getLatitude());
                if (!streetName.equals("")) {
                    getCamearaOnStreet(streetName);
                }
                boolean wasPlacedInQue = false;
                if(counter<postion.size()){
                    wasPlacedInQue = handler.postDelayed(this, delay);
                }
                counter++;
            }
        });

    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d("DEMOGPS", "Street Name :" + location.getLatitude() + " latitude : " + location.getLongitude());
    }

    //Do draw route
    private String getDirectionURL(LatLng ori , LatLng dest){

        RequestParams params = getParams(ori,dest,"driving");
        HttpUtils.getByUrl("https://maps.googleapis.com/maps/api/directions/json",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                List<List<HashMap<String, String>>> routes = null;
                try {
                    DirectionsJSONParser parser = new DirectionsJSONParser();
                    routes = parser.parse(response);
                    Log.d("GGMAP",routes.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("GGMAP",responseString.toString());
            }
        });
        return "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=10.838751,%20106.648976" +
                "&destination=10.852706,%20106.629692" +
                "&mode=driving" +
                "&key=AIzaSyAsQvoX3tvrOxlSA0Xv77ptHcTK9tGm5yA";
    }



    private RequestParams getParams(LatLng ori, LatLng des,String mode) {
        RequestParams params = new RequestParams();
        params.add("origin", ori.latitude+","+ori.longitude);
        params.add("destination", des.latitude+","+des.longitude);
        params.add("mode", mode);
        params.add("key", getResources().getString(R.string.google_maps_key));
        return params;
    }

}
