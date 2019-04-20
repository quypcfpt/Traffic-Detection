package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Report {
    private int id;
    private Integer status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Camera cameraByCameraId;
    private String imageUrl;
    private Date date;

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
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id &&
                Objects.equals(status, report.status) &&
                Objects.equals(startTime, report.startTime) &&
                Objects.equals(endTime, report.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, startTime, endTime);
    }

    @ManyToOne
    @JoinColumn(name = "camera_id", referencedColumnName = "id")
    public Camera getCameraByCameraId() {
        return cameraByCameraId;
    }

    public void setCameraByCameraId(Camera cameraByCameraId) {
        this.cameraByCameraId = cameraByCameraId;
    }

    @Basic
    @Column(name = "image_url", nullable = true, length = 2147483647)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
