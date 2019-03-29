package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
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
    private EditText editText;
    private int currentPage, totalPage;
    private boolean isScrolling = false;
    private LinearLayoutManager mLayoutManager;
    private static List<StreetModel> streetModelList;
    private int currentItems, scrollOutItems, totalItems, id;
    private TextView labelTextView , textErr;
    private ProgressBar progressBar;
    private ImageButton btnBack;
    private String currentPostion;
    private LatLng currenLatLng;
    @SuppressLint("WrongViewCast")
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Intent intent = getIntent();
        String label = intent.getStringExtra("STREET_NAME");
        labelTextView.setText(label);
        String street_id = intent.getStringExtra("STREET_ID");
        currentPostion =intent.getStringExtra("POSITION");
        if(currentPostion != null){
            String[] item = currentPostion.split("_");
            currenLatLng = new LatLng(Double.parseDouble(item[0]),Double.parseDouble(item[1]));
            Log.d("Position",currentPostion.toString());
        }
        id = Integer.parseInt(street_id);
        onLoadList();
        btnBack.setOnClickListener(this);
    }

    private void onLoadList() {
        Call<Response<MultiCameraModel>> responseCall = apiInterface.loadCamerasByStreet(id);
        responseCall.enqueue(new Callback<Response<MultiCameraModel>>() {
            @Override
            public void onResponse(Call<Response<MultiCameraModel>> call, retrofit2.Response<Response<MultiCameraModel>> response) {
                Response<MultiCameraModel> res = response.body();

                final MultiCameraModel multiCameraModel = res.getData();
                final List<CameraModel> cameraModelList = multiCameraModel.getCameraList();
                if (!cameraModelList.isEmpty()) {
                    if(currenLatLng!=null){
                        List<CameraModel> cameraList =  new ArrayList<>();
                        for(CameraModel item  : cameraModelList){
                            LatLng position = getCameraLocation(item.getPosition());
                            float distance = calculationByDistance(currenLatLng,position); // calculate distance from current device location wo camera on street
                            cameraList.add(new CameraModel(item.getId(),item.getDescription(),item.getPosition(),item.getResource(),item.getObserverStatus(),
                                    item.getCamOrder(),item.getStreet(),item.getImgUrl(),item.getTime(),distance)
                            );

                        }
                        adapter = new CameraAdapter(cameraList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        textErr.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }else{
                        adapter = new CameraAdapter(cameraModelList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        textErr.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(ListCameraActivity.this, "This Street doesn't have any camera. We will update soon.", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    textErr.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {

            }
        });

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
}
