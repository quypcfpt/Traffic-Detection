package com.spring2019.mobile.trafficJamWarningSystem.model;

import com.google.gson.annotations.Expose;

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
    private boolean isActive;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
