package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class StreetModel {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String district;
    @Expose
    private String city;
    @Expose
    private List<CameraModel> cameraList;

    public StreetModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<CameraModel> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<CameraModel> cameraList) {
        this.cameraList = cameraList;
    }
}
