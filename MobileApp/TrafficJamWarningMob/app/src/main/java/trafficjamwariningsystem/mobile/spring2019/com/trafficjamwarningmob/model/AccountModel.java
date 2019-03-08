package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

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
    private int role_id;
    @Expose
    private int status;

    public AccountModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
