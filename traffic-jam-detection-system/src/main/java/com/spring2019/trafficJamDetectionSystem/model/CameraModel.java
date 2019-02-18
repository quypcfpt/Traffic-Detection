package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

public class CameraModel {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private Double latitude;
    @Expose
    private Double longtitude;
    @Expose
    private Integer order;
    @Expose
    private Integer width;
    @Expose
    private StreetModel street;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public StreetModel getStreet() {
        return street;
    }

    public void setStreet(StreetModel street) {
        this.street = street;
    }
}
