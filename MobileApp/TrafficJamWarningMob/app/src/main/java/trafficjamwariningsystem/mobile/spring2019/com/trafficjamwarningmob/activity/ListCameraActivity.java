package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



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

public class ListCameraActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    private static CameraAdapter adapter;
    private RecyclerView recyclerView;
    private EditText editText;
    private int currentPage ,totalPage;
    private boolean isScrolling = false;
    private LinearLayoutManager mLayoutManager;
    private static List<StreetModel> streetModelList;
    private int currentItems, scrollOutItems, totalItems , id;

    private ProgressBar progressBar;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_camera);

        recyclerView=(RecyclerView) findViewById(R.id.listCamera);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Intent intent = getIntent();
        String message =intent.getStringExtra("STREET_NAME");
        String street_id = intent.getStringExtra("STREET_ID");
        id = Integer.parseInt(street_id);
        onLoadList();
    }

    private void onLoadList(){

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
                }else{
                    Toast.makeText(ListCameraActivity.this, "This Street doesn't have any camera. We will update soon.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<MultiCameraModel>> call, Throwable t) {

            }
        });
    }


}
