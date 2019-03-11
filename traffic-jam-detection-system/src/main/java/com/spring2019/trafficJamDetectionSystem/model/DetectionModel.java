package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

public class DetectionModel {

    @Expose
    private int CameraId;
    @Expose
    private int statusId;
    @Expose
    private String imageUrl;
    @Expose
    private String time;

    public DetectionModel() {
    }

    public int getCameraId() {
        return CameraId;
    }

    public void setCameraId(int cameraId) {
        CameraId = cameraId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

