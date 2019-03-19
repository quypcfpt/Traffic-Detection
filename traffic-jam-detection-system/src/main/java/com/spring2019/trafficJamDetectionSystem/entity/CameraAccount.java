package com.spring2019.trafficJamDetectionSystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Camera_Account", schema = "dbo", catalog = "casptonetrafficjamdb")
public class CameraAccount {
    private int id;
    private Camera cameraByCameraId;
    private Account accountByAccountId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraAccount that = (CameraAccount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "camera_id", referencedColumnName = "id", nullable = false)
    public Camera getCameraByCameraId() {
        return cameraByCameraId;
    }

    public void setCameraByCameraId(Camera cameraByCameraId) {
        this.cameraByCameraId = cameraByCameraId;
    }

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    public Account getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(Account accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
