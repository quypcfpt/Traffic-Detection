package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Camera {
    private int id;
    private String description;
    private String position;
    private String resource;
    private Integer observedStatus;
    private Integer camOrder;
    private Boolean isActive;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "position", nullable = true, length = 255)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "resource", nullable = true, length = 255)
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Basic
    @Column(name = "observed_status", nullable = true)
    public Integer getObservedStatus() {
        return observedStatus;
    }

    public void setObservedStatus(Integer observedStatus) {
        this.observedStatus = observedStatus;
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
    @Column(name = "isActive", nullable = true)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return id == camera.id &&
                Objects.equals(description, camera.description) &&
                Objects.equals(position, camera.position) &&
                Objects.equals(resource, camera.resource) &&
                Objects.equals(observedStatus, camera.observedStatus) &&
                Objects.equals(camOrder, camera.camOrder) &&
                Objects.equals(isActive, camera.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, position, resource, observedStatus, camOrder, isActive);
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