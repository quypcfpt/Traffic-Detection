package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiBookmarkCameraModel {
    @Expose
    private int bookmarkId;
    @Expose
    private List<CameraModel> cameraList;

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public List<CameraModel> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<CameraModel> cameraList) {
        this.cameraList = cameraList;
    }
}