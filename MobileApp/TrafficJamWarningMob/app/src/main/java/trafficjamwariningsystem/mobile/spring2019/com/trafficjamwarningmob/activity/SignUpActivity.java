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
                    txtError.setText("Tên tài khoản phải từ 6-32 ký tự");
                }else if(!edtUsername.getText().toString().trim().matches("[a-zA-Z0-9]+")){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Tên tài khoản không được chứa các ký tự đặc biệt như (!,@,#,$,%,^,&,*)");
                }else if (edtPassword.getText().toString().trim().length()>16 || edtPassword.getText().toString().trim().length()<6){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Mật khẩu phải từ 6-16 ký tự");
                }else if (!edtConfirmPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Xác nhận mật khẩu phải trùng với mật khẩu");
                }else if(edtFullName.getText().toString().trim().length()>32 || edtFullName.getText().toString().trim().length()<1){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Họ và Tên phải từ 1-32 ký tự");
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
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                SignUpActivity.this.finish();
                            } else {
                                txtError.setVisibility(View.VISIBLE);
                                txtError.setText("Tên Tài Khoản đã tồn tại");
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<String>> call, Throwable t) {
                            Toast.makeText(SignUpActivity.this, "Xin kiểm tra lại kết nối Internet", Toast.LENGTH_LONG).show();
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

