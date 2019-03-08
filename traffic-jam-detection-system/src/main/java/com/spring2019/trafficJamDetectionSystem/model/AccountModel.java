package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

public class AccountModel {
    @Expose
    private int id;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private Integer roleId;

    public AccountModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
