package com.spring2019.mobile.trafficJamWarningSystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.adapter.CameraAdapter;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiClient;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiInterface;
import com.spring2019.mobile.trafficJamWarningSystem.model.CameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.MultiCameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.Response;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListCameraActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    private static CameraAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPage ,totalPage;
    private LinearLayoutManager mLayoutManager;
    private boolean isScrolling = false;
    private static List<StreetModel> streetModelList;
    int currentItems, scrollOutItems, totalItems;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_camera);

        recyclerView=(RecyclerView) findViewById(R.id.listCamera);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(this);
        initialView();

    }
    public void initialView(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Intent intent = getIntent();
        String message =intent.getStringExtra("STREET_NAME");
        String street_id = intent.getStringExtra("STREET_ID");
        int id = Integer.parseInt(street_id);
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        Call<Response<MultiCameraModel>> responseCall = apiInterface.loadCamerasByStreet(id);
        responseCall.enqueue(new Callback<Response<MultiCameraModel>>() {
            @Override
            public void onResponse(Call<Response<MultiCameraModel>> call, retrofit2.Response<Response<MultiCameraModel>> response) {
                Response<MultiCameraModel> res = response.body();
                final MultiCameraModel multiCameraModel=res.getData();
                final List<CameraModel> cameraModelList =multiCameraModel.getCameraList();
                if (cameraModelList != null) {
                    adapter=new CameraAdapter(cameraModelList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {

            }
        });
    }
}
