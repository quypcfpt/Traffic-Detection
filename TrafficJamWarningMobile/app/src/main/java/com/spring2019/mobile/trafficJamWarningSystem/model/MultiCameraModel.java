package com.spring2019.mobile.trafficJamWarningSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiCameraModel {
    @Expose
    List<CameraModel> cameraList;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

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
