package com.spring2019.mobile.trafficJamWarningSystem.api;

import com.spring2019.mobile.trafficJamWarningSystem.model.CameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.ImageModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.MultiCameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.MultiStreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  ApiInterface {
    //Street
    @GET("api/street/{district}")
    Call<Response<MultiStreetModel>> getStreets(@Path("district") String district);

    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy")String sortBy);

    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy")String sortBy , @Query("page") int page);

    //Camera
    @GET("api/camera/{id}")
    Call<Response<CameraModel>> loadCameraById(@Path("id") Integer id);

    @GET("api/camera")
    Call<Response<MultiCameraModel>> loadAllCameras(@Query("sortBy")String sortBy);

    @GET("api/camera/streetId/{streetId}")
    Call<Response<MultiCameraModel>> loadCamerasByStreet(@Path("streetId")int streetId);

    @GET("api/camera/streetId/{streetId}")
    Call<Response<MultiCameraModel>> loadCamerasByStreet2(@Path("streetId")int streetId,@Query("sortBy")String sortBy);

    //Image
    @GET("api/camera/image/{id}")
    Call<Response<ImageModel>> loadImageByCameraId(@Path("id") Integer id);
}
