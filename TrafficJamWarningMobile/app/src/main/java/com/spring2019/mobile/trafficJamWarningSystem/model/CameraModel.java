package com.spring2019.mobile.trafficJamWarningSystem.model;

import com.google.gson.annotations.Expose;

public class CameraModel {
    @Expose
    private int id;

    @Expose
    private String code;

    @Expose
    private float latitude;

    @Expose
    private float longtitude;

    @Expose
    private int camOrder;

    @Expose
    private int width;

    @Expose
    private int street_id;

    @Expose
    private String image;

    @Expose
    private int status;

    public CameraModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public int getCamOrder() {
        return camOrder;
    }

    public void setCamOrder(int camOrder) {
        this.camOrder = camOrder;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStreet_id() {
        return street_id;
    }

    public void setStreet_id(int street_id) {
        this.street_id = street_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
