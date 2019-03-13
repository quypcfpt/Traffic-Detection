package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.ImageModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    TextView cameraId, imageTime, labelTextView;
    ImageButton btnBack;
    ImageView cameraStatus, imageRoad;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        final int cameraID = Integer.parseInt(intent.getStringExtra("CAMERA_ID"));
        final int status = Integer.parseInt(intent.getStringExtra("CAMERA_STATUS"));
         String cameraName = intent.getStringExtra("CAMERA_NAME");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        labelTextView=(TextView)findViewById(R.id.labelTextView);
        cameraId = (TextView) findViewById(R.id.cameraId);
        imageRoad = (ImageView) findViewById(R.id.imageRoad);
        imageTime = (TextView) findViewById(R.id.imageTime);
        btnBack=(ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        cameraStatus = (ImageView) findViewById(R.id.cameraStatus);
        labelTextView.setText(cameraName);
        final String imagePath = "https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/headers/ws_cropper/1_0x0_790x520_0x520_traffic_jams.jpg";
        cameraId.setText(cameraID+"");
        cameraStatus.setImageResource(status==1?R.mipmap.green:R.mipmap.red);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                ImageActivity.this.finish();
                break;
        }
    }
}