package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraInBookmarkAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiBookmarkCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultipleBookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.DirectionsJSONParser;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.HttpUtils;

public class SearchRouteActitvity extends Fragment implements LocationListener {

    ApiInterface apiInterface;
    Boolean locationPermission = false;
    LocationManager locationManager;
    String locationProvider = "";
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 3;
    private static CameraInBookmarkAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private EditText ori;
    private EditText des;
    private CheckBox cbGPS;
    private LatLng ori_coordinate;
    private LatLng des_coordinate;
    private Button saveBookmark;
    private Button viewMap;
    private TextView empty;
    private ProgressBar pb;
    private ArrayList<CameraModel> onRouteCamera;
    private ArrayList<LatLng> onRoutePoints;
    private String oriStr = "";
    private String desStr = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_route_actitvity, container, false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        onRouteCamera = new ArrayList<>();
        onRoutePoints = new ArrayList<>();
        ori = rootView.findViewById(R.id.txtOri);
        des = rootView.findViewById(R.id.txtDes);
        cbGPS = rootView.findViewById(R.id.cbGPS);
        recyclerView = rootView.findViewById(R.id.listResult);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        final Button searchCamera = rootView.findViewById(R.id.btnSearchCamera);
        saveBookmark = rootView.findViewById(R.id.btnSaveBookmark);
        viewMap = rootView.findViewById(R.id.btnViewMap);
        empty = rootView.findViewById(R.id.txtEmpty);
        pb = rootView.findViewById(R.id.pbSearching);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        final ViewPager vp = getActivity().findViewById(R.id.container);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if(i == 1 && empty.getVisibility() == View.GONE){
                    if(fileExist()) {
                        checkExistedBookmark();
                    } else {
                        requestLogin();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        searchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSearchInfo();
                if(cbGPS.isChecked()){
                    if (!des.getText().toString().trim().isEmpty()) {
                        checkLocationService();
                    }
                }else {
                    if(!ori.getText().toString().trim().isEmpty() && !des.getText().toString().trim().isEmpty()) {
                        pb.setVisibility(View.VISIBLE);
                        RequestParams params = getParams(ori.getText().toString(), des.getText().toString());
                        searchCamera(params);
                    }
                }

            }
        });
        cbGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbGPS.isChecked()) {
                    checkLocationAccessPermission();
                }
                Log.d("Check", cbGPS.isChecked() + "");

            }
        });
        saveBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileExist()) {
                    createBookmark();
                } else {
                    vp.setCurrentItem(2);
                }
            }
        });
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("POINTS", onRoutePoints);
                bundle.putSerializable("CAMERAS", onRouteCamera);
                bundle.putString("ORI", oriStr);
                bundle.putString("DES", desStr);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;

    }

    private void resetSearchInfo(){
        saveBookmark.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        viewMap.setVisibility(View.GONE);
        ori_coordinate = null;
        des_coordinate = null;
        oriStr = "";
        desStr = "";
        if(onRouteCamera != null)
            onRouteCamera.clear();
    }

    private RequestParams getParams(String ori, String des) {
        RequestParams params = new RequestParams();
        params.add("origin", ori);
        params.add("destination", des);
        params.add("key", getResources().getString(R.string.google_maps_key));
        return params;
    }

    public void searchCamera(RequestParams params) {
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
                    pb.setVisibility(View.GONE);
                    if(points.isEmpty()){
                        onEmptyResult();
                    } else {
                        onRoutePoints = points;
                        getOnRouteCamera(points);
                    }
                }
            }
        });

    }

    private void getOnRouteCamera(final List<LatLng> points) {
        ori_coordinate = points.get(0);
        des_coordinate = points.get(points.size() - 1);
        Log.d("ori", ori_coordinate + "");
        Log.d("des", des_coordinate + "");

        Call<Response<MultiCameraModel>> responseCall = apiInterface.loadAllCameras("id");
        responseCall.enqueue(new Callback<Response<MultiCameraModel>>() {
            @Override
            public void onResponse(Call<Response<MultiCameraModel>> call, retrofit2.Response<Response<MultiCameraModel>> response) {
                Response<MultiCameraModel> res = response.body();
                MultiCameraModel multiCameraModel = res.getData();
                if (multiCameraModel != null) {
                    ArrayList<CameraModel> cameras = (ArrayList) multiCameraModel.getCameraList();

                    for (CameraModel x : cameras) {
                        String[] xPosArr = x.getPosition().split(",");
                        LatLng xPos = new LatLng(Double.parseDouble(xPosArr[0]), Double.parseDouble(xPosArr[1]));
                        if (PolyUtil.isLocationOnPath(xPos, points, false, 10)) {
                            onRouteCamera.add(x);
                        }
                    }
                    if (onRouteCamera.isEmpty()) {
                        onEmptyResult();
                    } else {
                        oriStr = ori.getText().toString();
                        desStr = des.getText().toString();
                        if (fileExist()) {
                            checkExistedBookmark();
                        } else {
                            requestLogin();
                        }
                        adapter = new CameraInBookmarkAdapter(onRouteCamera, getContext());
                        recyclerView.setAdapter(adapter);
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
                ori_coordinate = null;
                des_coordinate = null;
            }
        });
    }

    private void onEmptyResult() {
        empty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        saveBookmark.setVisibility(View.GONE);
        viewMap.setVisibility(View.GONE);
    }

    private void checkLocationAccessPermission() {
        int accessCoarsePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFinePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

        } else {
            Toast.makeText(getActivity(), "GPS Access: Ready", Toast.LENGTH_SHORT).show();
            cbGPS.setChecked(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    CheckBox cbGPS = (CheckBox) getActivity().findViewById(R.id.cbGPS);
                    Toast.makeText(getActivity(), "GPS Access: Ready", Toast.LENGTH_SHORT).show();
                    cbGPS.setChecked(true);
                } else {
                    Toast.makeText(getActivity(), "You need to permit GPS Access", Toast.LENGTH_SHORT).show();
                    cbGPS.setChecked(false);
                }
                break;
        }
    }

    private void checkLocationService() {
        locationProvider = getBestEnabledLocationProvider();
        if (locationProvider.equals("passive")) {
            Toast.makeText(getActivity(), "You need to turn on GPS service and try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Locating your position...", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private String getBestEnabledLocationProvider() {
        String bestProvider;
        Criteria criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        return bestProvider;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng newLocation = new LatLng(location.getLatitude(), location.getLongitude());
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        locationManager.removeUpdates(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(newLocation.latitude, newLocation.longitude, 5);
            if (addresses != null) {
                Address add = addresses.get(0);
                String strAdd = "";
                String subthorough = "";
                String thorough = "";
                String feature = "";
                String subadmin = "";
                for (int i = 0; i < addresses.size(); i++) {
                    subthorough = addresses.get(i).getSubThoroughfare();
                    thorough = addresses.get(i).getThoroughfare();
                    feature = addresses.get(i).getFeatureName();
                    subadmin = addresses.get(i).getSubAdminArea();
                    if (subthorough != null && thorough != null && subadmin != null) {
                        strAdd = subthorough + " " + thorough + " " + subadmin;
                        break;
                    } else if (feature != null && thorough != null && subadmin != null) {
                        strAdd = feature + " " + thorough + " " + subadmin;
                        break;
                    }
                }
                if (strAdd.equals("")) {
                    strAdd = addresses.get(0).getAddressLine(0);
                }
                ori.setText(strAdd);
                des.requestFocus();
                Log.d("STREET ADDRESS", addresses.get(0).getAddressLine(0));
            } else {
                Log.d("street", "null address");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Toast.makeText(getActivity(), "Located your position", Toast.LENGTH_SHORT).show();
        RequestParams params = getParams(newLocation.latitude + ", " + newLocation.longitude, des.getText().toString());
        searchCamera(params);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void checkExistedBookmark(){

        if (ori_coordinate == null && des_coordinate == null ) {
            return;
        }
        Integer userId = getUserId();
        if (userId == null) {
            Toast.makeText(getActivity(), "ERROR: Can not check existed bookmark", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<Response<List<BookmarkModel>>> responseCall = apiInterface.getBookMakByAccountId(userId);
        responseCall.enqueue(new Callback<Response<List<BookmarkModel>>>() {
            @Override
            public void onResponse(Call<Response<List<BookmarkModel>>> call, retrofit2.Response<Response<List<BookmarkModel>>> response) {
                Response<List<BookmarkModel>> res = response.body();
                List<BookmarkModel> bookmarkList = res.getData();
                String strOri = ori_coordinate.latitude + "," + ori_coordinate.longitude;
                String strDes = des_coordinate.latitude + "," + des_coordinate.longitude;
                for (BookmarkModel x : bookmarkList) {
                    if (x.getOri_coordinate().equals(strOri) && x.getDes_coordinate().equals(strDes)) {
                        saveBookmark.setVisibility(View.VISIBLE);
                        saveBookmark.setText("Added bookmark");
                        saveBookmark.setClickable(false);
                        viewMap.setVisibility(View.VISIBLE);
                        return;
                    }

                }
                saveBookmark.setVisibility(View.VISIBLE);
                saveBookmark.setText("+ Add to bookmark");
                saveBookmark.setClickable(true);
                viewMap.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Response<List<BookmarkModel>>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }

    public void createBookmark() {
        BookmarkModel newBookmarkModel = new BookmarkModel();
        Integer userId = getUserId();
        if (userId == null) {
            Toast.makeText(getActivity(), "ERROR: Can not add bookmark", Toast.LENGTH_SHORT).show();
            return;
        }
        newBookmarkModel.setAccountId(userId);
        newBookmarkModel.setOrigin(ori.getText().toString());
        newBookmarkModel.setDestination(des.getText().toString());
        newBookmarkModel.setOri_coordinate(ori_coordinate.latitude + "," + ori_coordinate.longitude);
        newBookmarkModel.setDes_coordinate(des_coordinate.latitude + "," + des_coordinate.longitude);
        Call<Response<BookmarkModel>> responseCall = apiInterface.createBookmark(newBookmarkModel);
        responseCall.enqueue(new Callback<Response<BookmarkModel>>() {
            @Override
            public void onResponse(Call<Response<BookmarkModel>> call, retrofit2.Response<Response<BookmarkModel>> response) {
                Response<BookmarkModel> res = response.body();
                BookmarkModel resModel = res.getData();
                if (resModel != null) {
                    Toast.makeText(getActivity(), "Added a new bookmark", Toast.LENGTH_SHORT).show();
                    saveBookmark.setVisibility(View.VISIBLE);
                    saveBookmark.setText("Added bookmark");
                    saveBookmark.setClickable(false);
                    viewMap.setVisibility(View.VISIBLE);
                    Log.d("BOOKMARK INFO", resModel.getId() +"");
                    MultiBookmarkCameraModel multiBookmarkCameraModel = new MultiBookmarkCameraModel();
                    multiBookmarkCameraModel.setBookmarkId(resModel.getId());
                    multiBookmarkCameraModel.setCameraList(onRouteCamera);
                    Call<Response> responseCall = apiInterface.saveBookmarkCamera(multiBookmarkCameraModel);
                    responseCall.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Response res = response.body();
                            if(res.getData() != null){
                                Log.d("BOOK_CAM_RES INFO", res.getData() +"");
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Log.d("Failure", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Response<BookmarkModel>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }

    public Integer getUserId() {
        Integer userId = null;
        try {
            FileInputStream fileInputStream = getContext().openFileInput("accountInfo");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                buffer.append(lines);
            }
            userId = Integer.parseInt(buffer.toString().split("-")[0]);
            Log.d("Login Info", buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RequestLogin-Error", e.getMessage());
        }
        return userId;
    }

    public void requestLogin() {
        if (ori_coordinate == null && des_coordinate == null) {
            return;
        }
        saveBookmark.setText("Login to add bookmark");
        saveBookmark.setVisibility(View.VISIBLE);
        saveBookmark.setClickable(true);
        viewMap.setVisibility(View.VISIBLE);

    }

    public boolean fileExist() {
        File file = getContext().getFileStreamPath("accountInfo");
        return file.exists();
    }

}

