package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera,Integer> {

    Optional<Camera> getByIdAndIsActive(int id, Boolean isActive);

    Page<Camera> findAllByIsActive(Boolean isActive,Pageable pageable);
    Page<Camera> findByStreetByStreetIdAndIsActive(Integer id,Boolean isActive,Pageable pageable);

    Page<Camera> findByStreetByStreetIdAndIsActive(Street street, Boolean isActive, Pageable pageable);
}
