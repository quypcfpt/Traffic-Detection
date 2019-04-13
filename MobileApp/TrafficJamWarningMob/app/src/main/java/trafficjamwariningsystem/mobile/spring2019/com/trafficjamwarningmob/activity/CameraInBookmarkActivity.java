package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
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
    private BookmarkModel bookmark;
    private ImageButton btnBack;
    private ProgressBar progress;
    private Button btnViewMap;
    private LinearLayout viewHeader;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_in_bookmark);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        lbHeader = findViewById(R.id.lbHeader);
        recyclerView = findViewById(R.id.listCamera);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        btnViewMap = findViewById(R.id.btnViewMap);
        viewHeader = findViewById(R.id.viewHeader);
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
        bookmark = (BookmarkModel) intent.getSerializableExtra("bookmark");
        lbHeader.setText(bookmark.getOrigin() + " - " + bookmark.getDestination());
        String[] pointArr = bookmark.getRoute_points().split("-");
        ArrayList<LatLng> points = new ArrayList<>();
        for(int i = 0; i < pointArr.length; i++){
            Double lat = Double.parseDouble(pointArr[i].split(",")[0]);
            Double lon = Double.parseDouble(pointArr[i].split(",")[1]);
            points.add(new LatLng(lat, lon));
        }
        getCameraListAndInitMapButton(points);
    }

    //get camera list in bookmark and change view
    private void getCameraListAndInitMapButton(final ArrayList<LatLng> points) {
        Log.d("ori", bookmark.getOri_coordinate() + "");
        Log.d("des", bookmark.getDes_coordinate() + "");

        Call<Response<List<CameraModel>>> responseCall = apiInterface.getCameraInBookmark(bookmark.getId());
        responseCall.enqueue(new Callback<Response<List<CameraModel>>>() {
            @Override
            public void onResponse(Call<Response<List<CameraModel>>> call, retrofit2.Response<Response<List<CameraModel>>> response) {
                Response<List<CameraModel>> res = response.body();
                final ArrayList<CameraModel> inBookmarkCameras = (ArrayList) res.getData();

                if (!inBookmarkCameras.isEmpty()) {
                    adapter = new CameraInBookmarkAdapter(inBookmarkCameras, getApplicationContext());
                    progress.setVisibility(View.GONE);
                    btnViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CameraInBookmarkActivity.this, MapsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("POINTS", points);
                            bundle.putSerializable("CAMERAS", inBookmarkCameras);
                            bundle.putString("ORI", lbHeader.getText().toString().split(" - ")[0]);
                            bundle.putString("DES", lbHeader.getText().toString().split(" - ")[1]);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    viewHeader.setVisibility(View.VISIBLE);
                    btnViewMap.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Response<List<CameraModel>>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
                progress.setVisibility(View.GONE);
                Toast.makeText(CameraInBookmarkActivity.this, "LỖI: Không tải được danh sách camera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = getReceiver();
        LocalBroadcastManager.getInstance(CameraInBookmarkActivity.this).registerReceiver((receiver),
                new IntentFilter("Camera")
        );
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
                    if(recyclerView.getVisibility() == View.VISIBLE){
                        List<CameraModel> list = adapter.getDataSet();
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).getId() == cameraId){
                                list.get(i).setObserverStatus(status);
                                adapter.notifyItemChanged(i);
                                break;
                            }
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