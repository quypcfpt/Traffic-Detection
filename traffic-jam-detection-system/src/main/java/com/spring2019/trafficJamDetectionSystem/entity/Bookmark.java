package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bookmark {
    private int id;
    private Integer accountId;
    private String origin;
    private String destination;
    private String ori_coordinate;
    private String des_coordinate;
    private String oriCoordinate;
    private String desCoordinate;
    private Account accountByAccountId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "account_id", nullable = true)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "origin", nullable = true, length = 2147483647)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String routeJson) {
        this.origin = routeJson;
    }

    @Basic
    @Column(name = "destination", nullable = true, length = 2147483647)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "ori_coordinate", nullable = true, length = 2147483647)
    public String getOri_coordinate() {
        return ori_coordinate;
    }

    public void setOri_coordinate(String ori_coordinate) {
        this.ori_coordinate = ori_coordinate;
    }

    @Basic
    @Column(name = "des_coordinate", nullable = true, length = 2147483647)
    public String getDes_coordinate() {
        return des_coordinate;
    }

    public void setDes_coordinate(String des_coordinate) {
        this.des_coordinate = des_coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
                Objects.equals(accountId, bookmark.accountId) &&
                Objects.equals(origin, bookmark.origin)&&
                Objects.equals(ori_coordinate, bookmark.ori_coordinate)&&
                Objects.equals(des_coordinate, bookmark.des_coordinate)&&
                Objects.equals(destination, bookmark.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, origin,destination,ori_coordinate,des_coordinate);
    }

}
