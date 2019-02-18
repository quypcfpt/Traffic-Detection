package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Camera implements Serializable {
    private int id;
    private String name;
    private Double latitude;
    private Double longtitude;
    private Integer camOrder;
    private Integer width;
    private Street streetByStreetId;

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
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "latitude", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longtitude", nullable = true, precision = 0)
    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    @Basic
    @Column(name = "camOrder", nullable = true)
    public Integer getCamOrder() {
        return camOrder;
    }

    public void setCamOrder(Integer camOrder) {
        this.camOrder = camOrder;
    }

    @Basic
    @Column(name = "width", nullable = true)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return id == camera.id &&
                Objects.equals(name, camera.name) &&
                Objects.equals(latitude, camera.latitude) &&
                Objects.equals(longtitude, camera.longtitude) &&
                Objects.equals(camOrder, camera.camOrder) &&
                Objects.equals(width, camera.width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longtitude, camOrder, width);
    }

    @ManyToOne
    @JoinColumn(name = "street_id", referencedColumnName = "id")
    public Street getStreetByStreetId() {
        return streetByStreetId;
    }

    public void setStreetByStreetId(Street streetByStreetId) {
        this.streetByStreetId = streetByStreetId;
    }
}
