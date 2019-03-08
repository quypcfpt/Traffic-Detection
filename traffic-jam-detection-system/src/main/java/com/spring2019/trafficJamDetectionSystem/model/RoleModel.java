package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class RoleModel {
    @Expose
    private int id;
    @Expose
    private String role;
    @Expose
    private List<RoleModel> list;

    public List<RoleModel> getList() {
        return list;
    }

    public void setList(List<RoleModel> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RoleModel() {

    }
}
