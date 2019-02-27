package com.spring2019.mobile.trafficJamWarningSystem.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.adapter.StreetAdapter;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiClient;
import com.spring2019.mobile.trafficJamWarningSystem.api.ApiInterface;
import com.spring2019.mobile.trafficJamWarningSystem.model.MultiStreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.Response;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.utils.EndlessRecyclerOnScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ApiInterface apiInterface;
    private static StreetAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPage ,totalPage;
    private LinearLayoutManager mLayoutManager;
    private boolean isScrolling = false;
    private static List<StreetModel> streetModelList;
    int currentItems, scrollOutItems, totalItems;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView) findViewById(R.id.recycleView);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(this);
        onFirstLoad();
        recyclerView.setLayoutManager(mLayoutManager);
        doLoadMore();


    }

    public void onFirstLoad () {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<MultiStreetModel>> responseCall = apiInterface.getAllStreets("district");
        responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
            @Override
            public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                Response<MultiStreetModel> res = response.body();

                MultiStreetModel multiStreetModel = res.getData();
                currentPage = multiStreetModel.getCurrentPage();
                totalPage = multiStreetModel.getTotalPage();
                long totalRecord = multiStreetModel.getTotalRecord();
                streetModelList =multiStreetModel.getStreetList();
                if (streetModelList != null) {
                    adapter = new StreetAdapter(streetModelList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {

            }
        });

    }
    private void loadMore(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<StreetModel> list = null;
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
               currentPage +=  1;
                if (currentPage <= totalPage) {
                    Call<Response<MultiStreetModel>> responseCall = apiInterface.getAllStreets("district", currentPage);
                    responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {

                        @Override
                        public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                            Response<MultiStreetModel> res = response.body();
                            final MultiStreetModel multiStreetModel = res.getData();
                            for (StreetModel item : multiStreetModel.getStreetList()) {
                                streetModelList.add(item);
                            }
                            if (streetModelList != null) {
                                adapter = new StreetAdapter(streetModelList,getApplicationContext());
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Nothing to load more", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 5000);

    }
    private void doLoadMore(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();

                if(isScrolling &&(currentItems+scrollOutItems == totalItems)){
                    isScrolling=false;
                    loadMore();
                }
            }
        });
    }
}