package com.spring2019.trafficJamDetectionSystem.repository;


import com.spring2019.trafficJamDetectionSystem.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> getByCameraId(int id);
}
