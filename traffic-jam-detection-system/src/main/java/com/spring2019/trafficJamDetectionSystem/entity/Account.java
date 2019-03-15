package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Account {
    private int id;
    private String username;
    private String password;
    private String name;
    private Integer roleId;

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
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(name, account.name) &&
                Objects.equals(roleId, account.roleId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, roleId);
    }
}
