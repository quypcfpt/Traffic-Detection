package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.StreetModel;


public class StreetListActivity extends Fragment implements View.OnClickListener {
    ApiInterface apiInterface;
    private static StreetAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPage, totalPage, currenPageSearch, totalPageSearch;
    private LinearLayoutManager mLayoutManager;
    private boolean isScrolling = false;
    private static List<StreetModel> streetModelList, streetModelListSearch;
    private Button btnSearch;
    private EditText editText;
    int currentItems, scrollOutItems, totalItems;
    private ProgressBar progressBar;
    private String txtSearch;
    private RelativeLayout view;
    private TextView txtError;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = new View(getActivity());
        v = inflater.inflate(R.layout.fragment_street_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        editText = (EditText) v.findViewById(R.id.txtSearch);
        btnSearch = (Button) v.findViewById(R.id.searchBtn);
        btnSearch.setBackgroundResource(R.mipmap.search);
        view = (RelativeLayout) v.findViewById(R.id.listview);
        txtError = (TextView) v.findViewById(R.id.txtError);
        editText.clearFocus();
        onFirstLoad();
        doLoadMore();
        btnSearch.setOnClickListener(this);
        return v;
    }

    public void onFirstLoad() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<MultiStreetModel>> responseCall = apiInterface.getAllStreets("district", 12);
        responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
            @Override
            public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                Response<MultiStreetModel> res = response.body();
                MultiStreetModel multiStreetModel = res.getData();
                if (multiStreetModel == null)
                    return;
                currentPage = multiStreetModel.getCurrentPage();
                totalPage = multiStreetModel.getTotalPage();
                long totalRecord = multiStreetModel.getTotalRecord();
                streetModelList = multiStreetModel.getStreetList();
                if (!streetModelList.isEmpty()) {
                    view.setVisibility(View.VISIBLE);
                    txtError.setVisibility(View.GONE);
                    adapter = new StreetAdapter(streetModelList, getContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    txtError.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {
//                Log.d("Failure", t.getMessage());
            }
        });
    }

    private void loadMore() {
        txtSearch = editText.getText() + "";
        if (txtSearch.equals("")) {
            if (currentPage + 1 <= totalPage) {
                progressBar.setVisibility(View.VISIBLE);
                currentPage += 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<StreetModel> list = null;
                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Response<MultiStreetModel>> responseCall = apiInterface.getAllStreets("district", currentPage, 12);
                        responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
                            @Override
                            public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                                Response<MultiStreetModel> res = response.body();
                                final MultiStreetModel multiStreetModel = res.getData();
                                for (StreetModel item : multiStreetModel.getStreetList()) {
                                    streetModelList.add(item);
                                }
                                if (streetModelList != null) {
                                    view.setVisibility(View.VISIBLE);
                                    txtError.setVisibility(View.GONE);
                                    adapter = new StreetAdapter(streetModelList, getContext());
                                    mLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    txtError.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Nothing to load more", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            }

                            @Override
                            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {

                            }
                        });
                    }
                }, 3000);
            } else {
                Toast.makeText(getContext(), "Nothing to load more", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        } else {
            if (currenPageSearch + 1 <= totalPageSearch) {
                currenPageSearch += 1;
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Response<MultiStreetModel>> responseCall = apiInterface.getStreetsBySearchNameLike(txtSearch, "district", currenPageSearch);
                        responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
                            @Override
                            public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                                Response<MultiStreetModel> res = response.body();
                                MultiStreetModel multiStreetModel = res.getData();
                                currenPageSearch = multiStreetModel.getCurrentPage();
                                for (StreetModel item : multiStreetModel.getStreetList()) {
                                    streetModelList.add(item);
                                }
                                if (streetModelList != null) {
                                    view.setVisibility(View.VISIBLE);
                                    txtError.setVisibility(View.GONE);
                                    adapter = new StreetAdapter(streetModelList, getContext());
                                    recyclerView.setAdapter(adapter);
                                    mLayoutManager = new LinearLayoutManager(getContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {
                                Log.d("Failure", t.getMessage());
                            }
                        });
                    }
                }, 3000);
            } else {
                Toast.makeText(getContext(), "Nothing to load more", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void doLoadMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    loadMore();
                }
            }
        });
    }

    private void onButtonClick() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenPageSearch = 1;
                doSearch();
            }
        });
    }

    private void doSearch() {
        txtSearch = editText.getText() + "";
        if (!txtSearch.equals("")) {
            Call<Response<MultiStreetModel>> responseCall = apiInterface.getStreetsBySearchNameLike(txtSearch, "district", 1);
            responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
                @Override
                public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                    Response<MultiStreetModel> res = response.body();
                    MultiStreetModel multiStreetModel = res.getData();
                    currenPageSearch = multiStreetModel.getCurrentPage();
                    totalPageSearch = multiStreetModel.getTotalPage();
                    long totalRecord = multiStreetModel.getTotalRecord();
                    streetModelList = multiStreetModel.getStreetList();
                    if (streetModelList != null && !streetModelList.isEmpty()) {
                        adapter = new StreetAdapter(streetModelList, getContext());
                        recyclerView.setAdapter(adapter);
                    } else {
                        txtError.setText(txtSearch + " cannot found .");
                        txtError.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                    }
                }


                @Override
                public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {
                    Log.d("Failure", t.getMessage());
                }
            });
        } else {
            onFirstLoad();
        }
    }

    @Override
    public void onClick(View v) {
        doSearch();
    }
}
