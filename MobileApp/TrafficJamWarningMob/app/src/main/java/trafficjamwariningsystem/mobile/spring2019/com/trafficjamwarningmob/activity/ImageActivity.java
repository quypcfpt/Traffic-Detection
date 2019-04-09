package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.ImageModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    TextView cameraId, imageTime, labelTextView,txtStatus;
    ImageButton btnBack;
    ImageView cameraStatus, imageRoad;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        txtStatus=(TextView) findViewById(R.id.txtStatusView);
        labelTextView = (TextView) findViewById(R.id.labelTextView);
        cameraId = (TextView) findViewById(R.id.cameraId);
        imageRoad = (ImageView) findViewById(R.id.imageRoad);
        imageTime = (TextView) findViewById(R.id.imageTime);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        cameraStatus = (ImageView) findViewById(R.id.cameraStatus);
        final String imagePath = "https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/headers/ws_cropper/1_0x0_790x520_0x520_traffic_jams.jpg";
        cameraId.setText(cameraID + "");
        cameraStatus.setImageResource(status == 0 ? R.mipmap.green : status ==1 ? R.mipmap.red : R.mipmap.yellow);
        txtStatus.setText(status == 0 ? "Bình Thường" : status ==1 ? "Kẹt" : "Đông");
        Call<Response<CameraModel>> responseCall = apiInterface.loadCameraById(cameraID);
        responseCall.enqueue(new Callback<Response<CameraModel>>() {
            @Override
            public void onResponse(Call<Response<CameraModel>> call, retrofit2.Response<Response<CameraModel>> response) {
                Response<CameraModel> res = response.body();
                final CameraModel model = res.getData();
                Picasso.get().load(model.getImgUrl()).fit().placeholder(R.mipmap.image).into(imageRoad);
                imageTime.setText(model.getTime() + "");
            }

            @Override
            public void onFailure(Call<Response<CameraModel>> call, Throwable t) {
                Log.d("Failure", t.getMessage());
        Bundle bundle =getIntent().getExtras();
        String listCamJsonObj = bundle.getString("CAMINFO");
        CameraModel camResult = new CameraModel();
        if(listCamJsonObj !=null){
            Log.d("JSON",listCamJsonObj);
            try {
                camResult = parseJsonintoList(listCamJsonObj);
                labelTextView.setText(camResult.getDescription());
                cameraId.setText(camResult.getId() + "");
                cameraStatus.setImageResource(camResult.getObserverStatus() == 0 ? R.mipmap.green : camResult.getObserverStatus() ==1 ? R.mipmap.red : R.mipmap.yellow);
                txtStatus.setText(camResult.getObserverStatus() == 0 ? "Bình Thường" : camResult.getObserverStatus() ==1 ? "Kẹt" : "Đông");
                Picasso.get().load(camResult.getImgUrl()).fit().placeholder(R.mipmap.image).into(imageRoad);
                imageTime.setText(camResult.getTime() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                ImageActivity.this.finish();
                break;
        }
    }

    public CameraModel parseJsonintoList(String jsonObject) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonObject);
        int id = jsonObj.getInt("id");
        String description = jsonObj.getString("description");
        int observerStatus = jsonObj.getInt("observerStatus");
        String position = jsonObj.getString("position");
        String imgUrl = jsonObj.getString("imgUrl");
        String timeImage = jsonObj.getString("time");
        CameraModel resultObj = new CameraModel(id,description,position,observerStatus,imgUrl,timeImage);
        return resultObj;
    }
}