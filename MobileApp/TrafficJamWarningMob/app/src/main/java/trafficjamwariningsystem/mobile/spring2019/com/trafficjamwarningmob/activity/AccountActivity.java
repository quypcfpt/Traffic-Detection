package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;


public class AccountActivity extends Fragment implements View.OnClickListener {
private ApiInterface apiInterface;
private boolean isLogin ;
private TextView txtSignUp;
private EditText edtUsername,edtPassword;
private Button btnSignIn;
private LinearLayout signInLayout,accountLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_account, container, false);
        signInLayout=(LinearLayout) v.findViewById(R.id.viewSignIn);
        accountLayout=(LinearLayout)v.findViewById(R.id.viewAccount);
        txtSignUp=(TextView)v.findViewById(R.id.txtSignUp);
        edtUsername=(EditText)v.findViewById(R.id.txtUsername);
        edtPassword=(EditText)v.findViewById(R.id.txtPassword);
        btnSignIn=(Button)v.findViewById(R.id.btnSignIn);
        isLogin=false;
        initialView(isLogin);
        txtSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        return v;
    }

    private void initialView(boolean isLogin){
        if(isLogin){
            signInLayout.setVisibility(View.GONE);
            accountLayout.setVisibility(View.VISIBLE);
        }else{
            signInLayout.setVisibility(View.VISIBLE);
            accountLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                String username=edtUsername.getText()+"";
                String password=edtPassword.getText()+"";
                Toast.makeText(v.getContext(), "Username :" + username +"  Password : " + password, Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtSignUp:
                Intent intent = new Intent(v.getContext(),SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean checkLogin(String userName , String passowrd){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<AccountModel>> responseCall = apiInterface.checkUserLogin(userName,passowrd);
        responseCall.enqueue(new Callback<Response<AccountModel>>() {
            @Override
            public void onResponse(Call<Response<AccountModel>> call, retrofit2.Response<Response<AccountModel>> response) {
                String message = response.body().toString();
            }

            @Override
            public void onFailure(Call<Response<AccountModel>> call, Throwable t) {

            }
        });
        return false;
    }
}
