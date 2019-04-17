package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private EditText edtUsername, edtPassword, edtFullName, edtConfirmPassword;
    private boolean isSuccess;
    private ImageButton btnBack;
    private Button btnSignIn;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        edtFullName = (EditText) findViewById(R.id.txtFullName);
        edtUsername = (EditText) findViewById(R.id.txtUsername);
        edtPassword = (EditText) findViewById(R.id.txtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);
        txtError = (TextView) findViewById(R.id.txtRegisError);
        txtError.setVisibility(View.GONE);
        btnBack.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    public void clickToSignUp() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject acountModel = new JSONObject();
        try {
                acountModel.put("username", edtUsername.getText() + "");
                acountModel.put("password", edtPassword.getText() + "");
                acountModel.put("name", edtFullName.getText() + "");
                if(edtUsername.getText().toString().trim().length()>32 || edtUsername.getText().toString().trim().length()<6){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("The UserName must in 3-32 charaters");
                }else if(!edtUsername.getText().toString().trim().matches("[a-zA-Z0-9]+")){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("The UserName does not contain specical character (!,@,#,$,%,^,&,*)");
                }else if (edtPassword.getText().toString().trim().length()>16 || edtPassword.getText().toString().trim().length()<6){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("The Passwrod must in 6-16 charaters");
                }else if (!edtConfirmPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("The Confirm Password have to match Password");
                }else if(edtFullName.getText().toString().trim().length()>32 || edtFullName.getText().toString().trim().length()<1){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("The Full Name must in 1-32 charaters");
                }else{
                    txtError.setVisibility(View.GONE);
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
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText("UserName is Existed");
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
        switch (v.getId()) {
            case R.id.btnBack:
                SignUpActivity.this.finish();
                break;
            case R.id.btnSignIn:
                clickToSignUp();

                break;
        }
    }
}

