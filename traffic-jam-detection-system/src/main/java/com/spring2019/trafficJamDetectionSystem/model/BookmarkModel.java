package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

public class BookmarkModel {
    @Expose
    private int id;
    @Expose
    private Integer accountId;
    @Expose
    private String origin;
    @Expose
    private String destination;
    @Expose
    private String ori_coordinate;
    @Expose
    private String des_coordinate;
    @Expose
    private String route_points;

    public BookmarkModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOri_coordinate() {
        return ori_coordinate;
    }

    public void setOri_coordinate(String ori_coordinate) {
        this.ori_coordinate = ori_coordinate;
    }

    public String getDes_coordinate() {
        return des_coordinate;
    }

    public void setDes_coordinate(String des_coordinate) {
        this.des_coordinate = des_coordinate;
    }

    public String getRoute_points() {
        return route_points;
    }

    public void setRoute_points(String route_points) {
        this.route_points = route_points;
    }

    @Override
    public String toString() {
        return "BookmarkModel{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", ori_coordinate='" + ori_coordinate + '\'' +
                ", des_coordinate='" + des_coordinate + '\'' +
                '}';
    }
}
