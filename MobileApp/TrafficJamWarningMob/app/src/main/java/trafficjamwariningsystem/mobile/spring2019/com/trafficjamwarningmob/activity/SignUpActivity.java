package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface;
    private EditText edtUsername,edtPassword ,edtFullName,edtConfirmPassword;
    private boolean isSuccess;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnBack=(ImageButton)findViewById(R.id.btnBack);
        edtFullName=(EditText)findViewById(R.id.txtFullName);
        edtUsername=(EditText)findViewById(R.id.txtUsername);
        edtPassword=(EditText)findViewById(R.id.txtPassword);
        edtConfirmPassword=(EditText)findViewById(R.id.txtConfirmPassword);
        btnBack.setOnClickListener(this);
    }

    public void clickToSignUp(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject acountModel = new JSONObject();
        try {
            if(!edtPassword.equals(edtConfirmPassword)){
                Toast.makeText(this, "Password and Confirm Password do not match !!!", Toast.LENGTH_SHORT).show();
            }else {
                acountModel.put("username", edtUsername.getText() + "");
                acountModel.put("password", edtPassword.getText() + "");
                acountModel.put("name", edtFullName.getText() + "");
                Call<Response<String>> responseCall = apiInterface.createNewAccount(acountModel.toString());
                responseCall.enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        Response<String> message = response.body();
                        isSuccess = Boolean.parseBoolean(message.getData());
                        String ne;
                        if (isSuccess) {
                            SignUpActivity.this.finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "UserName is Existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                SignUpActivity.this.finish();
                break;
        }
    }
}

