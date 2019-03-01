package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Image {
    private int id;
    private Integer cameraId;
    private String link;
    private Timestamp time;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "camera_id", nullable = true)
    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    @Basic
    @Column(name = "link", nullable = true, length = 255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Objects.equals(cameraId, image.cameraId) &&
                Objects.equals(link, image.link) &&
                Objects.equals(time, image.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cameraId, link, time);
    }
}
