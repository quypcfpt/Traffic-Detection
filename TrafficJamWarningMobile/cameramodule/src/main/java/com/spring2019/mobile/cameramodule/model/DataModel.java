package com.spring2019.mobile.cameramodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @Expose
    @SerializedName("name")
    String name;

    @Expose
    @SerializedName("type")
    String type;

    @Expose
    @SerializedName("version_number")
    String versionNumber;

    @Expose
    @SerializedName("status")
    int status;



    public DataModel(String name, String type, String versionNumber, int status ) {
        this.name=name;
        this.type=type;
        this.versionNumber=versionNumber;
        this.status=status;

    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public String getVersionNumber() {
        return versionNumber;
    }


    public int getStatus() {
        return status;
    }

}
