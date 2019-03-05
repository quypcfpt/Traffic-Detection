package com.spring2019.mobile.trafficJamWarningSystem.model;

import com.google.gson.annotations.Expose;

public class CameraModel {
    @Expose
    private int id;

    @Expose
    private String description;

    @Expose
    private String position;

    @Expose
    private String resource;

    @Expose
    private int observerStatus;

    @Expose
    private int camOrder;

    @Expose
    private float street_id;


    public CameraModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getObserverStatus() {
        return observerStatus;
    }

    public void setObserverStatus(int observerStatus) {
        this.observerStatus = observerStatus;
    }

    public int getCamOrder() {
        return camOrder;
    }

    public void setCamOrder(int camOrder) {
        this.camOrder = camOrder;
    }

    public float getStreet_id() {
        return street_id;
    }

    public void setStreet_id(float street_id) {
        this.street_id = street_id;
    }


}
