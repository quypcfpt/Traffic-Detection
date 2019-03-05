package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.StreetAdapter;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.StreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;


public class StreetListActivity extends Fragment {
    ApiInterface apiInterface;
    private static StreetAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPage, totalPage;
    private LinearLayoutManager mLayoutManager;
    private boolean isScrolling = false;
    private static List<StreetModel> streetModelList;
    int currentItems, scrollOutItems, totalItems;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = new View(getActivity());
        v = inflater.inflate(R.layout.fragment_street_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        onFirstLoad();
        doLoadMore();
        return v;
    }

    public void onFirstLoad() {
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
                streetModelList = multiStreetModel.getStreetList();
                if (streetModelList != null) {
                    adapter = new StreetAdapter(streetModelList, getContext());
                    recyclerView.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {
                Log.d("Failure" , t.getMessage());
            }
        });

    }
    private void loadMore(){
        currentPage +=  1;
        if (currentPage <= totalPage) {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final List<StreetModel> list = null;
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
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
                                adapter = new StreetAdapter(streetModelList, getContext());
                                mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {

                        }
                    });
                }
                }
                , 500);
        }else{
            Toast.makeText(getContext(), "Nothing to load more", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
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
