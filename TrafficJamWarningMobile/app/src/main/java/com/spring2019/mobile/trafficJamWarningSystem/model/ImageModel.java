package com.spring2019.mobile.trafficJamWarningSystem.model;
import com.google.gson.annotations.Expose;

import java.sql.Timestamp;

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

    public ImageModel(int id, int cameraId, String link, String time) {
        this.id = id;
        this.cameraId = cameraId;
        this.link = link;
        this.time = time;
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
