package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.CameraAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraAccountRepository extends JpaRepository<CameraAccount, Integer> {
    List<CameraAccount> findByAccountByAccountId(Account account);

    List<CameraAccount> findByCameraByCameraId(Camera camera);
}
