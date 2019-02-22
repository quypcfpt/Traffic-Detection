package com.spring2019.mobile.trafficJamWarningSystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ApiInterface apiInterface;
    private static StreetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Response<MultiStreetModel>> responseCall = apiInterface.getStreets("Go Vap");
        responseCall.enqueue(new Callback<Response<MultiStreetModel>>() {
            @Override
            public void onResponse(Call<Response<MultiStreetModel>> call, retrofit2.Response<Response<MultiStreetModel>> response) {
                Response<MultiStreetModel> res = response.body();

                MultiStreetModel multiStreetModel = res.getData();

                Integer currentPage = multiStreetModel.getCurrentPage();
                Integer totalPage = multiStreetModel.getTotalPage();
                long totalRecord = multiStreetModel.getTotalRecord();

                List<StreetModel> streetModelList = multiStreetModel.getStreetList();

                Toast.makeText(getApplicationContext(), currentPage + " page\n"
                        + totalRecord + " total\n"
                        + totalPage + " totalPages\n", Toast.LENGTH_SHORT).show();

                if (streetModelList != null) {
                    adapter=new StreetAdapter(streetModelList,getApplicationContext());
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Response<MultiStreetModel>> call, Throwable t) {

            }
        });
    }
}