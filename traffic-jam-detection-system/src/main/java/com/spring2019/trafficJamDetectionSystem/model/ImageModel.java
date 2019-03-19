package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;


public class ImageModel {
    @Expose
    private int id;
    @Expose
    private int cameraId;
    @Expose
    private String link;
    @Expose
    private String time;

    public ImageModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
