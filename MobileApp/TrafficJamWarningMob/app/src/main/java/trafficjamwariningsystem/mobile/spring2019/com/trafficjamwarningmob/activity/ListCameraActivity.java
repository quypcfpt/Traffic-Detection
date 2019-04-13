package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.CameraAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.StreetModel;

public class ListCameraActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface;
    private static CameraAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout viewHeader;
    private LinearLayoutManager mLayoutManager;
    private static List<StreetModel> streetModelList;
    private int  id;
    private TextView labelTextView , textErr,txtDistance;
    private ProgressBar progressBar;
    private ImageButton btnBack;
    private List<CameraModel> mlistCamera=new ArrayList<>();
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_camera);
        labelTextView = (TextView) findViewById(R.id.labelTextView);
        recyclerView = (RecyclerView) findViewById(R.id.listCamera);
        mLayoutManager = new LinearLayoutManager(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        textErr = (TextView)findViewById(R.id.txtError);
        recyclerView.setLayoutManager(mLayoutManager);
        txtDistance=(TextView)findViewById(R.id.viewDistance);
        txtDistance.setVisibility(View.GONE);
        viewHeader = (LinearLayout)findViewById(R.id.viewHeader);
        viewHeader.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Intent intent = getIntent();
        Bundle bundle =getIntent().getExtras();
        String label = bundle.getString("STREET_NAME");
        labelTextView.setText(label);
        String street_id = bundle.getString("STREET_ID");
        String listCamJsonObj = bundle.getString("LIST");
        if(listCamJsonObj !=null){
            Log.d("JSON",listCamJsonObj);
            try {
                mlistCamera = parseJsonintoList(listCamJsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        id = Integer.parseInt(street_id);
        onLoadList();
        btnBack.setOnClickListener(this);
    }

    private void onLoadList() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        if(!mlistCamera.isEmpty()){
            adapter = new CameraAdapter(mlistCamera, getApplicationContext());
            recyclerView.setAdapter(adapter);
            textErr.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            txtDistance.setVisibility(View.VISIBLE);
            viewHeader.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            Call<Response<MultiCameraModel>> responseCall = apiInterface.loadCamerasByStreet(id);
            responseCall.enqueue(new Callback<Response<MultiCameraModel>>() {
                @Override
                public void onResponse(Call<Response<MultiCameraModel>> call, retrofit2.Response<Response<MultiCameraModel>> response) {
                    Response<MultiCameraModel> res = response.body();

                    final MultiCameraModel multiCameraModel = res.getData();
                    final List<CameraModel> cameraModelList = multiCameraModel.getCameraList();
                    if (!cameraModelList.isEmpty()) {
                            adapter = new CameraAdapter(cameraModelList, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            textErr.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            viewHeader.setVisibility(View.VISIBLE);
                            txtDistance.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ListCameraActivity.this, "This Street doesn't have any camera. We will update soon.", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        textErr.setVisibility(View.VISIBLE);
                        viewHeader.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {

                }
            });
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                ListCameraActivity.this.finish();
                break;
        }
    }
    private float calculationByDistance(LatLng startP, LatLng endP){
        float[] result =new float[1];
        Location.distanceBetween(startP.latitude,startP.longitude,endP.latitude,endP.longitude,result);
        Log.d("DISTANCE : ",result[0]+"");
        return result[0];
    }

    private LatLng getCameraLocation(String location){
        String[] parts = location.split(",");
        LatLng result = new LatLng(Double.parseDouble(parts[0]),Double.parseDouble(parts[1]));
        return result;
    }

    public List<CameraModel> parseJsonintoList(String jsonObject) throws JSONException {
        List<CameraModel> resultList = new ArrayList<>();
//        JSONObject jsonObj = new JSONObject(jsonObject);
        JSONArray contacts = new JSONArray(jsonObject);
        for (int i = 0 ; i< contacts.length();i++){
            JSONObject cam = contacts.getJSONObject(i);
            int id = cam.getInt("id");
            String description = cam.getString("description");
            String position = cam.getString("position");
            int observerStatus = cam.getInt("observerStatus");
            float distance = Float.parseFloat(cam.getString("distance"));
            resultList.add(new CameraModel(id,description,position,observerStatus,distance));
        }
        return resultList;
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = getReceiver();
        LocalBroadcastManager.getInstance(ListCameraActivity.this).registerReceiver((receiver),
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
