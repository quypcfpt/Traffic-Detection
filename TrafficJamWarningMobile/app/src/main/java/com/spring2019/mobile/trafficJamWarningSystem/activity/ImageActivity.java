package com.spring2019.mobile.trafficJamWarningSystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiClient;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiInterface;
import com.spring2019.mobile.trafficJamWarningSystem.model.CameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.ImageModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.Response;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;

public class ImageActivity extends AppCompatActivity {
    TextView cameraId, imageTime;
    ImageView cameraStatus, imageRoad;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        final int cameraID = Integer.parseInt(intent.getStringExtra("CAMERA_ID"));
        final int status = Integer.parseInt(intent.getStringExtra("CAMERA_STATUS"));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        cameraId = (TextView) findViewById(R.id.cameraId);
        imageRoad = (ImageView) findViewById(R.id.imageRoad);
        imageTime = (TextView) findViewById(R.id.imageTime);
        cameraStatus = (ImageView) findViewById(R.id.cameraStatus);
        final String imagePath = "https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/headers/ws_cropper/1_0x0_790x520_0x520_traffic_jams.jpg";
        cameraId.setText(cameraID+"");
        cameraStatus.setImageResource(status==1?R.mipmap.green:R.mipmap.red);
//        Picasso.get().load(imagePath).fit().placeholder(R.mipmap.image).into(imageRoad);
        Call<Response<ImageModel>> responseCall = apiInterface.loadImageByCameraId(1);
        responseCall.enqueue(new Callback<Response<ImageModel>>() {
            @Override
            public void onResponse(Call<Response<ImageModel>> call, retrofit2.Response<Response<ImageModel>> response) {
                Response<ImageModel> res = response.body();
                final ImageModel model=res.getData();
                Picasso.get().load(model.getLink()).fit().placeholder(R.mipmap.image).into(imageRoad);
                imageTime.setText(model.getTime()+"");
            }

            @Override
            public void onFailure(Call<Response<ImageModel>> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
    }
}