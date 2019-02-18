package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Street implements Serializable {
    private int id;
    private String name;
    private String district;
    private String city;
    private Collection<Camera> camerasById;

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
    @Column(name = "district", nullable = true, length = 255)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return id == street.id &&
                Objects.equals(name, street.name) &&
                Objects.equals(district, street.district) &&
                Objects.equals(city, street.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, district, city);
    }

    @OneToMany(mappedBy = "streetByStreetId")
    public Collection<Camera> getCamerasById() {
        return camerasById;
    }

    public void setCamerasById(Collection<Camera> camerasById) {
        this.camerasById = camerasById;
    }
}
