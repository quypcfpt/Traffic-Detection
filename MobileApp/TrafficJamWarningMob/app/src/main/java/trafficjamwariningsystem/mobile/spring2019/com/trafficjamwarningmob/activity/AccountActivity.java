package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.BookmarkAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;


public class AccountActivity extends Fragment implements View.OnClickListener {
    private ApiInterface apiInterface;
    private boolean isLogin, isCheckLogin, isLogout;
    private TextView txtSignUp, accountUsername, accountName, emptyView,txtSignInErr;
    private EditText edtUsername, edtPassword;
    private Button btnSignIn;
    private LinearLayout signInLayout, accountLayout;
    private AccountModel account;
    private static BookmarkAdapter adapter;
    private RecyclerView recyclerView;
    FileOutputStream outputStream;
    private ProgressBar progressBar, progressBar1;
    private LinearLayoutManager mLayoutManager;
    private ImageButton logOutbtn;
    AccountModel accountModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        signInLayout = (LinearLayout) v.findViewById(R.id.viewSignIn);
        accountLayout = (LinearLayout) v.findViewById(R.id.viewAccount);
        txtSignUp = (TextView) v.findViewById(R.id.txtSignUp);
        edtUsername = (EditText) v.findViewById(R.id.txtUsername);
        edtPassword = (EditText) v.findViewById(R.id.txtPassword);
        btnSignIn = (Button) v.findViewById(R.id.btnSignIn);
        accountUsername = (TextView) v.findViewById(R.id.viewUserName);
        accountName = (TextView) v.findViewById(R.id.viewAccountName);
        emptyView = (TextView) v.findViewById(R.id.emptyView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleViewBookmark);
        logOutbtn = (ImageButton) v.findViewById(R.id.btnLogout);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
        progressBar1 = (ProgressBar) v.findViewById(R.id.progress1);
        txtSignInErr = (TextView) v.findViewById(R.id.txtSignInError);
        txtSignInErr.setVisibility(View.GONE);
        txtSignUp.setPaintFlags(txtSignUp.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        isLogin = false;
        isLogout = false;

        String fileName = "accountInfo";
        File file = getContext().getFileStreamPath(fileName);
        //check internal file to do funtion auto login
        if (file.exists()) {
            recyclerView.setVisibility(View.GONE);
            progressBar1.setVisibility(View.VISIBLE);
            accountModel = readInternal();
            autoLogin(accountModel);
            loadAccountInfo(accountModel);

        } else {
            initialView(false);
        }

        return v;
    }

    // Update View when access Account Activity
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        txtSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        logOutbtn.setOnClickListener(this);
    }

    // Set View fo application
    private void initialView(boolean isLogin) {
        if (isLogin) {
            signInLayout.setVisibility(View.GONE);
            accountLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressBar1.setVisibility(View.VISIBLE);

        } else {
            signInLayout.setVisibility(View.VISIBLE);
            accountLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                progressBar.setVisibility(View.VISIBLE);
                final String username = edtUsername.getText() + "";
                final String password = edtPassword.getText() + "";

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkLogin(username, password);
                        progressBar.setVisibility(View.GONE);
                        edtPassword.setText("");
                    }
                }, 1000);


                break;
            case R.id.txtSignUp:
                edtPassword.setText("");
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                AccountModel accountModel = readInternal();
                FirebaseMessaging.getInstance().unsubscribeFromTopic(accountModel.getUsername());

                edtPassword.setText("");
                deleteFileExisted("accountInfo");
                signInLayout.setVisibility(View.VISIBLE);
                accountLayout.setVisibility(View.GONE);
                isLogin = false;
                break;
        }
    }

    // Check login by username and password
    private void checkLogin(String userName, String passowrd) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject acountModel = new JSONObject();
        try {
            acountModel.put("username", userName);
            acountModel.put("password", passowrd);
            Log.d("Check ", "userName  : " + userName + "- Password " + passowrd);
            Call<Response<AccountModel>> responseCall = apiInterface.checkUserLogin(acountModel.toString());
            responseCall.enqueue(new Callback<Response<AccountModel>>() {
                @Override
                public void onResponse(Call<Response<AccountModel>> call, retrofit2.Response<Response<AccountModel>> response) {
                    Response<AccountModel> message = response.body();
                    account = new AccountModel();
                    AccountModel data = message.getData();
                    if (data != null) {
                        FirebaseMessaging.getInstance().subscribeToTopic(data.getUsername());

                        account.setId(data.getId());
                        account.setUsername(data.getUsername());
                        account.setPassword(data.getPassword());
                        account.setName(data.getName());
                        Log.d("Check2 ", "ID : " + account.getId() + "- Username " + account.getUsername() + "- Name " + account.getName());
                        saveAccountInfoInternal(account);
                        isLogin = true;
                        loadAccountInfo(account);
                        initialView(isLogin);
                    } else {
                        isLogin = false;
                        initialView(isLogin);
                        txtSignInErr.setText("The UserName or Password is Invalid");
                    }
                }

                @Override
                public void onFailure(Call<Response<AccountModel>> call, Throwable t) {
//                    Log.e("ERROR", t.getMessage());
                }
            });
            Log.d("Check Is Login : ", isLogin + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Load Account info and list bookmark of account
    private void loadAccountInfo(AccountModel account) {
        if (account != null) {

            progressBar1.setVisibility(View.VISIBLE);
            accountName.setText(account.getName());
            accountUsername.setText(account.getUsername());
            int accountID = account.getId();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Response<List<BookmarkModel>>> responseCall = apiInterface.getBookMarkByAccountId(accountID);

            responseCall.enqueue(new Callback<Response<List<BookmarkModel>>>() {
                @Override
                public void onResponse(Call<Response<List<BookmarkModel>>> call, retrofit2.Response<Response<List<BookmarkModel>>> response) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    Response<List<BookmarkModel>> message = response.body();
                    final List<BookmarkModel> models = message.getData();
                    if (!models.isEmpty()) {
                        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        adapter = new BookmarkAdapter(models,getActivity().getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(adapter);
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar1.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        progressBar1.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Response<List<BookmarkModel>>> call, Throwable t) {
//                    Log.e("EROR",t.getMessage());
                }
            });
            recyclerView.setVisibility(View.VISIBLE);
            progressBar1.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    // do save id- username - password when login access
    public void saveAccountInfoInternal(AccountModel account) {
        String fileName = "accountInfo";
        deleteFileExisted(fileName);
        try {
            outputStream = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write((account.getId() + "-").getBytes());
            outputStream.write((account.getUsername() + "-").getBytes());
            outputStream.write(account.getPassword().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //do delete file internal
    public void deleteFileExisted(String fname) {
        String yourFilePath = getContext().getFilesDir() + "/" + "hello.txt";
        Log.d("localFile : ", yourFilePath);
        File file = getContext().getFileStreamPath(fname);
        file.delete();
    }

    private void autoLogin(AccountModel accountModel) {
        checkLogin(accountModel.getUsername(), accountModel.getPassword());
    }

    // read internal file to get username , userId , password
    private AccountModel readInternal() {
        try {
            AccountModel accountModel = new AccountModel();

            String username;
            String password;
            int id;
            String fileName = "accountInfo";
            FileInputStream fileIn = getContext().openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String s = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            String[] parts = s.split("-");
            id = Integer.parseInt(parts[0]);
            username = parts[1];
            password = parts[2];

            accountModel.setId(id);
            accountModel.setUsername(username);
            accountModel.setPassword(password);

            return accountModel;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
