package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Camera implements Serializable {
    private int id;
    private String code;
    private Double latitude;
    private Double longtitude;
    private Integer camOrder;
    private Integer width;
    private Street streetByStreetId;
    private Integer status;
    private String image;
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
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @Basic
    @Column(name = "image", nullable = true, length = 255)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return id == camera.id &&
                Objects.equals(code, camera.code) &&
                Objects.equals(latitude, camera.latitude) &&
                Objects.equals(longtitude, camera.longtitude) &&
                Objects.equals(camOrder, camera.camOrder) &&
                Objects.equals(width, camera.width)&&
                Objects.equals(status, camera.status)&&
                Objects.equals(image, camera.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, latitude, longtitude, camOrder, width,status,image);
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
