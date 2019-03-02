package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bookmark {
    private int id;
    private Integer accountId;
    private String routeJson;

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
    @Column(name = "route_json", nullable = true, length = 2147483647)
    public String getRouteJson() {
        return routeJson;
    }

    public void setRouteJson(String routeJson) {
        this.routeJson = routeJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
                Objects.equals(accountId, bookmark.accountId) &&
                Objects.equals(routeJson, bookmark.routeJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, routeJson);
    }
}
