package com.example.quy.myapplication;

public class DataModel {

    String name;
    String type;
    String version_number;
    int status;


    public DataModel(String name, String type, String version_number, int status ) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
        this.status=status;

    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public String getVersion_number() {
        return version_number;
    }


    public int getStatus() {
        return status;
    }



}