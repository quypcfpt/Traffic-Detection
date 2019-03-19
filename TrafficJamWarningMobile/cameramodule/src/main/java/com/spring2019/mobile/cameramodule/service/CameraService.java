package com.spring2019.mobile.cameramodule.service;

import com.spring2019.mobile.cameramodule.model.DataModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CameraService {

    @GET("")
    Single<DataModel> getDataModel();
}
