package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {

    Camera findById(int id);

    Optional<Camera> getByIdAndIsActive(int id, Boolean isActive);

    Page<Camera> findAllByIsActive(Boolean isActive, Pageable pageable);

    Page<Camera> findByStreetByStreetIdAndIsActive(Integer id, Boolean isActive, Pageable pageable);

    Page<Camera> findByStreetByStreetIdAndIsActive(Street street, Boolean isActive, Pageable pageable);

    List<Camera> findByStreetByStreetIdAndIsActive(Street street, Boolean isActive);

    List<Camera> findByStreetByStreetIdAndIsActive(Integer street, Boolean isActive);

    @Query(value = "SELECT * FROM Camera C JOIN Street S " +
            "ON S.id = C.street_id Where S.isActive = 1 AND S.name = :streetName", nativeQuery = true)
    List<Camera> findCameraByStreetName(@Param("streetName") String streetName);
}