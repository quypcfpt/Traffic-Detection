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
import android.widget.LinearLayout;
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
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraInBookmarkAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiBookmarkCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.DirectionsJSONParser;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.HttpUtils;

public class SearchRouteActitvity extends Fragment implements LocationListener {

    ApiInterface apiInterface;
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
    private LinearLayout viewHeader;


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
        viewHeader = rootView.findViewById(R.id.viewHeader);
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
                //change button when Direction tab is selected and last camera result is appearing
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
                    }else{
                        Toast.makeText(getActivity(), "Điểm đến chưa được nhập.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if(!ori.getText().toString().trim().isEmpty() && !des.getText().toString().trim().isEmpty()) {
                        pb.setVisibility(View.VISIBLE);
                        RequestParams params = getParams(ori.getText().toString(), des.getText().toString());
                        searchCamera(params);
                    }else{
                        Toast.makeText(getActivity(), "Điểm đi hoặc điểm đến chưa được nhập.", Toast.LENGTH_SHORT).show();
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

    //reset content of view when click check traffic button
    private void resetSearchInfo(){
        saveBookmark.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        viewMap.setVisibility(View.GONE);
        viewHeader.setVisibility(View.GONE);
        ori_coordinate = null;
        des_coordinate = null;
        oriStr = "";
        desStr = "";
        if(onRouteCamera != null)
            onRouteCamera.clear();
    }

    //get param for directon api request
    private RequestParams getParams(String ori, String des) {
        RequestParams params = new RequestParams();
        params.add("origin", ori);
        params.add("destination", des);
        params.add("key", getResources().getString(R.string.google_maps_key));
        return params;
    }

    //search camera on route from requested origin and destination
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

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", throwable.getMessage());
                pb.setVisibility(View.GONE);
                Toast.makeText(getContext(), "LỖI: Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //request all active camera and check which is on provided polyline then show result
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
                        //check camera on route
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
                        viewHeader.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
                Toast.makeText(getActivity(), "LỖI: Không kiểm tra được giao thông", Toast.LENGTH_SHORT).show();
                ori_coordinate = null;
                des_coordinate = null;
            }
        });
    }

    //change view content when camera result is empty
    private void onEmptyResult() {
        empty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        viewHeader.setVisibility(View.GONE);
        saveBookmark.setVisibility(View.GONE);
        viewMap.setVisibility(View.GONE);
    }

    //check location access permission of user's device when use current location
    private void checkLocationAccessPermission() {
        int accessCoarsePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFinePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

        } else {
            Toast.makeText(getActivity(), "Quyền GPS: Sẵn sàng", Toast.LENGTH_SHORT).show();
            cbGPS.setChecked(true);
        }
    }

    //process checkbox view from location access permission response of user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    CheckBox cbGPS = (CheckBox) getActivity().findViewById(R.id.cbGPS);
                    Toast.makeText(getActivity(), "Quyền GPS: Sẵn sàng", Toast.LENGTH_SHORT).show();
                    cbGPS.setChecked(true);
                } else {
                    Toast.makeText(getActivity(), "Bạn cần cấp quyền GPS.", Toast.LENGTH_SHORT).show();
                    cbGPS.setChecked(false);
                }
                break;
        }
    }

    //get location provider and request current location of user's device
    private void checkLocationService() {
        locationProvider = getBestEnabledLocationProvider();
        if (locationProvider.equals("passive")) {
            Toast.makeText(getActivity(), "Bạn cần bật dịch vụ GPS và thử lại.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Đang xác định vị trí...", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    //get location provider of user's device
    private String getBestEnabledLocationProvider() {
        String bestProvider;
        Criteria criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        return bestProvider;
    }

    //fill origin input and search camera with located current location and inputed destination
    @Override
    public void onLocationChanged(Location location) {
        LatLng newLocation = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("NEW LOCATION", newLocation.latitude + "," + newLocation.longitude);
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        locationManager.removeUpdates(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(newLocation.latitude, newLocation.longitude, 5);
            if (addresses != null) {
                String strAdd = "";
                String subthorough = "";
                String thorough = "";
                String subadmin = "";
                for (int i = 0; i < addresses.size(); i++) {
                    Log.d("STREET PART", addresses.get(i).getSubThoroughfare() + " " + addresses.get(i).getThoroughfare() + " " + addresses.get(i).getSubAdminArea());
                    Log.d("STREET ADDRESS", addresses.get(i).getAddressLine(0));
                }
                for (int i = 0; i < addresses.size(); i++) {
                    subthorough = addresses.get(i).getSubThoroughfare();
                    thorough = addresses.get(i).getThoroughfare();
                    subadmin = addresses.get(i).getSubAdminArea();
                    if (subthorough != null && thorough != null && subadmin != null) {
                        if(!subthorough.trim().equals("#")){
                            strAdd = subthorough + " " + thorough + " " + subadmin;
                            break;
                        }
                    }else{
                        if(strAdd.equals("") && !addresses.get(i).getAddressLine(0).contains("Unnamed Road")){
                            strAdd = addresses.get(i).getAddressLine(0);
                            strAdd = getShortAddress(strAdd);
                        }
                    }
                }
                Toast.makeText(getActivity(), "Đã xác định vị trí hiện tại của bạn.", Toast.LENGTH_SHORT).show();
                ori.setText(strAdd);
                des.requestFocus();
                RequestParams params = getParams(newLocation.latitude + ", " + newLocation.longitude, des.getText().toString());
                searchCamera(params);
            } else {
                Log.d("street", "null address");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getGeoApiAddress(newLocation);
        }
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

    //check searched camera list is whether a bookmark and change bookmark button
    public void checkExistedBookmark(){

        if (ori_coordinate == null && des_coordinate == null ) {
            return;
        }
        Integer userId = getUserId();
        if (userId == null) {
            Toast.makeText(getActivity(), "LỖI: Không thể kiểm tra bookmark", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<Response<List<BookmarkModel>>> responseCall = apiInterface.getBookMarkByAccountId(userId);
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
                        saveBookmark.setText("Bookmark đã có");
                        saveBookmark.setClickable(false);
                        viewMap.setVisibility(View.VISIBLE);
                        return;
                    }

                }
                saveBookmark.setVisibility(View.VISIBLE);
                saveBookmark.setText("+ Thêm bookmark");
                saveBookmark.setClickable(true);
                viewMap.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Response<List<BookmarkModel>>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
                Toast.makeText(getActivity(), "LỖI: Không thể kiểm tra bookmark", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //add new bookmark and camera list in it
    public void createBookmark() {
        BookmarkModel newBookmarkModel = new BookmarkModel();
        Integer userId = getUserId();
        if (userId == null) {
            Toast.makeText(getActivity(), "LỖI: Không thể thêm bookmark", Toast.LENGTH_SHORT).show();
            return;
        }
        if(ori.getText().toString().trim().equals("")){
            Toast.makeText(getActivity(), "Nhập điểm đi để lưu.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(des.getText().toString().trim().equals("")){
            Toast.makeText(getActivity(), "Nhập điểm đến để lưu.", Toast.LENGTH_SHORT).show();
            return;
        }
        newBookmarkModel.setAccountId(userId);
        newBookmarkModel.setOrigin(ori.getText().toString().trim());
        newBookmarkModel.setDestination(des.getText().toString().trim());
        newBookmarkModel.setOri_coordinate(ori_coordinate.latitude + "," + ori_coordinate.longitude);
        newBookmarkModel.setDes_coordinate(des_coordinate.latitude + "," + des_coordinate.longitude);
        String route_points = "";
        for(int i = 0; i < onRoutePoints.size() - 1; i++){
            if(i == onRoutePoints.size() - 1){
                route_points += onRoutePoints.get(i).latitude + "," + onRoutePoints.get(i).longitude;
            }else{
                route_points += onRoutePoints.get(i).latitude + "," + onRoutePoints.get(i).longitude + "-";
            }
        }
        newBookmarkModel.setRoute_points(route_points);
        MultiBookmarkCameraModel multiBookmarkCameraModel = new MultiBookmarkCameraModel();
        multiBookmarkCameraModel.setBookmark(newBookmarkModel);
        multiBookmarkCameraModel.setCameraList(onRouteCamera);
        Call<Response<String>> responseCall = apiInterface.createBookmark(multiBookmarkCameraModel);
        responseCall.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> res = response.body();
                String isSuccess = res.getData();
                if (isSuccess.equals("1")) {
                    Toast.makeText(getActivity(), "Đã thêm mới bookmark", Toast.LENGTH_SHORT).show();
                    saveBookmark.setVisibility(View.VISIBLE);
                    saveBookmark.setText("Bookmark đã có");
                    saveBookmark.setClickable(false);
                    viewMap.setVisibility(View.VISIBLE);
                }else if(isSuccess.equals("0")){
                    Toast.makeText(getActivity(), "LỖI: Không thể thêm bookmark", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "LỖI: Không thể thêm bookmark", Toast.LENGTH_SHORT).show();
                Log.d("Failure", t.getMessage());
            }
        });
    }

    //get user id from internal storage of user's device
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

    //change view and content of bookmark button
    public void requestLogin() {
        if (ori_coordinate == null && des_coordinate == null) {
            return;
        }
        saveBookmark.setText("Đăng nhập để thêm bookmark");
        saveBookmark.setVisibility(View.VISIBLE);
        saveBookmark.setClickable(true);
        viewMap.setVisibility(View.VISIBLE);

    }

    //check file existed or not
    public boolean fileExist() {
        File file = getContext().getFileStreamPath("accountInfo");
        return file.exists();
    }

    private String getShortAddress(String add){
        int pos = add.indexOf(", Hồ Chí Minh");
        if(pos < 0){
            add = add.substring(0, add.indexOf(", Vietnam"));
        }else{
            add = add.substring(0, pos);
        }
        return add;
    }

    private void getGeoApiAddress(final LatLng location){
        String latlng = location.latitude + "," + location.longitude;
        HttpUtils.getByUrl("https://maps.googleapis.com/maps/api/geocode/json", getGeoParams(latlng), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String add = response.getJSONArray("results").getJSONObject(0).get("formatted_address") + "";
                    Log.d("GEO_API ADDRESS", add);
                    if(add != null){
                        if(!add.equals("")){
                            add = getShortAddress(add);
                        }
                        Toast.makeText(getActivity(), "Đã xác định vị trí hiện tại của bạn.", Toast.LENGTH_SHORT).show();
                        ori.setText(add);
                        des.requestFocus();
                        RequestParams params = getParams(location.latitude + ", " + location.longitude, des.getText().toString());
                        searchCamera(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", throwable.getMessage());
                pb.setVisibility(View.GONE);
                Toast.makeText(getContext(), "LỖI: Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RequestParams getGeoParams(String latlng) {
        RequestParams params = new RequestParams();
        params.add("latlng", latlng);
        params.add("key", getResources().getString(R.string.google_maps_key));
        return params;
    }


}

