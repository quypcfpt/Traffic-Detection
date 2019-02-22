package com.spring2019.mobile.trafficJamWarningSystem.api;

import com.spring2019.mobile.trafficJamWarningSystem.model.MultiStreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.Response;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  ApiInterface {

    @GET("api/street/{district}")
    Call<Response<MultiStreetModel>> getStreets(@Path("district") String district);
}
