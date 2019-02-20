package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DetectionModel {
    @Expose
    private int CameraId;

    @Expose
    private List<Detection> result;

    public int getCameraId() {
        return CameraId;
    }

    public void setCameraId(int cameraId) {
        CameraId = cameraId;
    }

    public List<Detection> getResult() {
        return result;
    }

    public void setResult(List<Detection> result) {
        this.result = result;
    }
}
class Detection{
    @Expose
    private int typeId;
    @Expose
    private int countVeh;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCountVeh() {
        return countVeh;
    }

    public void setCountVeh(int countVeh) {
        this.countVeh = countVeh;
    }
}
