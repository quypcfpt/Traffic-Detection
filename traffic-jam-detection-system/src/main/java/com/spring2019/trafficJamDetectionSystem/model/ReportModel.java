package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class ReportModel {

    @Expose
    private int id;
    @Expose
    private CameraModel camera;
    @Expose
    private String imgUrl;
    @Expose
    private int status;
    @Expose
    private String startTime;
    @Expose
    private String endTime;
    @Expose
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CameraModel getCamera() {
        return camera;
    }

    public void setCamera(CameraModel camera) {
        this.camera = camera;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
