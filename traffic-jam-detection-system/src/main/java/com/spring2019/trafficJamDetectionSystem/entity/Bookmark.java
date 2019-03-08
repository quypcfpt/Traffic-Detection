package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bookmark {
    private int id;
    private Integer accountId;
    private String origin;
    private String destination;

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
    public String getRouteJson() {
        return origin;
    }

    public void setRouteJson(String routeJson) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
                Objects.equals(accountId, bookmark.accountId) &&
                Objects.equals(origin, bookmark.origin)&&
                Objects.equals(destination, bookmark.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, origin,destination);
    }
}
