package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.BookmarkAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultipleBookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;


public class AccountActivity extends Fragment implements View.OnClickListener {
private ApiInterface apiInterface;
private boolean isLogin,isCheckLogin ;
private TextView txtSignUp, accountUsername , accountPassword,accountName;
private EditText edtUsername,edtPassword;
private Button btnSignIn;
private LinearLayout signInLayout,accountLayout;
private AccountModel account;
private static BookmarkAdapter adapter;
private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager mLayoutManager;
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
        accountUsername=(TextView) v.findViewById(R.id.viewUserName);
        accountPassword=(TextView) v.findViewById(R.id.viewPassword);
        accountName = (TextView)v.findViewById(R.id.viewAccountName);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleViewBookmark);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        isLogin=false;
        account = new AccountModel();
        initialView(isLogin);
        txtSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        return v;
    }

    private void initialView(boolean isLogin){
        if(isLogin){
            signInLayout.setVisibility(View.GONE);
            accountLayout.setVisibility(View.VISIBLE);
            loadAccountInfo();
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
                checkLogin(username,password);
                initialView(isCheckLogin);
                break;
            case R.id.txtSignUp:
                Intent intent = new Intent(v.getContext(),SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkLogin(String userName , String passowrd){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject acountModel = new JSONObject();
        try {
            acountModel.put("username",userName);
            acountModel.put("password",passowrd);
            Call<Response<AccountModel>> responseCall = apiInterface.checkUserLogin(acountModel.toString());
            responseCall.enqueue(new Callback<Response<AccountModel>>() {
                @Override
                public void onResponse(Call<Response<AccountModel>> call, retrofit2.Response<Response<AccountModel>> response) {
                    Response<AccountModel> message = response.body();
                    AccountModel data = message.getData();
                    if(data !=null){
                        account.setId(data.getId());
                        account.setUsername(data.getUsername());
                        account.setPassword(data.getPassword());
                        account.setName(data.getName());
                        isCheckLogin =true;
                    }else{
                        isCheckLogin = false;
                    }
                }
                @Override
                public void onFailure(Call<Response<AccountModel>> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadAccountInfo(){
        if(account !=null) {
            accountName.setText(account.getName());
            accountUsername.setText(account.getUsername());
            accountPassword.setText(account.getPassword());
            int accountID = account.getId();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Response<MultipleBookmarkModel>> responseCall = apiInterface.getBookMakByAccountId(accountID);
            responseCall.enqueue(new Callback<Response<MultipleBookmarkModel>>() {
                @Override
                public void onResponse(Call<Response<MultipleBookmarkModel>> call, retrofit2.Response<Response<MultipleBookmarkModel>> response) {
                    Response<MultipleBookmarkModel> message = response.body();
                    MultipleBookmarkModel data = message.getData();
                    List<BookmarkModel> models = data.getBookmarkModelList();
                    if(!models.isEmpty()){
                        adapter = new BookmarkAdapter(models, getContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<Response<MultipleBookmarkModel>> call, Throwable t) {

                }
            });
        }
    }
}
