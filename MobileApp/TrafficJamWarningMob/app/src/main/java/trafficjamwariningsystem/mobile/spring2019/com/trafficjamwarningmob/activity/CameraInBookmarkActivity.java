package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraInBookmarkAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.DirectionsJSONParser;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.HttpUtils;

public class CameraInBookmarkActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    private static CameraInBookmarkAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView lbHeader;
    private LatLng ori;
    private LatLng des;
    private String strOri;
    private String strDes;
    private ImageButton btnBack;
    private ProgressBar progress;
    private Button btnViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_in_bookmark);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        lbHeader = findViewById(R.id.lbHeader);
        recyclerView = findViewById(R.id.listCamera);
        recyclerView.setVisibility(View.GONE);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        btnViewMap = findViewById(R.id.btnViewMap);
        btnViewMap.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraInBookmarkActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        strOri = intent.getStringExtra("ORI");
        strDes = intent.getStringExtra("DES");
        String header = intent.getStringExtra("HEADER");
        lbHeader.setText(header);
        RequestParams params = getParams(strOri, strDes);
        searchCamera(params);
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
                            points.add(position);
                        }

                    }

                    if (!points.isEmpty()) {
                        getOnRouteCamera(points);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", throwable.getMessage());
                progress.setVisibility(View.GONE);
                Toast.makeText(CameraInBookmarkActivity.this, "LỖI: Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getOnRouteCamera(final ArrayList<LatLng> points) {
        ori = points.get(0);
        des = points.get(points.size() - 1);
        Log.d("ori", ori + "");
        Log.d("des", des + "");

        Call<Response<MultiCameraModel>> responseCall = apiInterface.loadAllCameras("id");
        responseCall.enqueue(new Callback<Response<MultiCameraModel>>() {
            @Override
            public void onResponse(Call<Response<MultiCameraModel>> call, retrofit2.Response<Response<MultiCameraModel>> response) {
                Response<MultiCameraModel> res = response.body();
                MultiCameraModel multiCameraModel = res.getData();
                if (multiCameraModel != null) {
                    ArrayList<CameraModel> cameras = (ArrayList) multiCameraModel.getCameraList();
                    final ArrayList<CameraModel> onRouteCameras = new ArrayList<>();
                    for (CameraModel x : cameras) {
                        String[] xPosArr = x.getPosition().split(",");
                        LatLng xPos = new LatLng(Double.parseDouble(xPosArr[0]), Double.parseDouble(xPosArr[1]));
                        if (PolyUtil.isLocationOnPath(xPos, points, false, 10)) {
                            onRouteCameras.add(x);
                        }
                    }
                    if (!onRouteCameras.isEmpty()) {
                        adapter = new CameraInBookmarkAdapter(onRouteCameras, getApplicationContext());
                        progress.setVisibility(View.GONE);
                        btnViewMap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CameraInBookmarkActivity.this, MapsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("POINTS", points);
                                bundle.putSerializable("CAMERAS", onRouteCameras);
                                bundle.putString("ORI", lbHeader.getText().toString().split(" - ")[0]);
                                bundle.putString("DES", lbHeader.getText().toString().split(" - ")[1]);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        btnViewMap.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
                progress.setVisibility(View.GONE);
                Toast.makeText(CameraInBookmarkActivity.this, "LỖI: Không tải được danh sách camera", Toast.LENGTH_SHORT).show();
            }
        });
    }
}