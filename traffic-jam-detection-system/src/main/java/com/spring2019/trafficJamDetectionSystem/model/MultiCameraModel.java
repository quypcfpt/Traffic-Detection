package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiCameraModel {

    @Expose
    private List<CameraModel> cameraList;
    @Expose
    private int totalPage;
    @Expose
    private long totalRecord;
    @Expose
    private int currentPage;

    public MultiCameraModel() {
    }

    public List<CameraModel> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<CameraModel> cameraList) {
        this.cameraList = cameraList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
