package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street, Integer> {

    List<Street> findAllByIsActive(boolean isActive);

    Page<Street> findAllByIsActive(Pageable pageable, boolean isActive);

    Page<Street> findByDistrictAndIsActive(String district, Boolean isActive, Pageable pageable);

    Page<Street> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);

    @Query(value = "SELECT DISTINCT  S.id, S.name, S.district, S.city, S.isActive " +
            "FROM Street S JOIN Camera C ON S.id = C.street_id WHERE S.isActive = ?1 AND C.isActive = ?2", nativeQuery = true)
    Page<Street> getValidStreet(Boolean isActive, Boolean cameraActive, Pageable pageable);

    @Query(value = "SELECT DISTINCT  S.id, S.name, S.district, S.city, S.isActive " +
            "FROM Street S JOIN Camera C ON S.id = C.street_id WHERE S.name LIKE %?1% AND S.isActive = ?2 AND C.isActive = ?3", nativeQuery = true)
    Page<Street> searchStreet(String name, Boolean isActive, Boolean cameraActive, Pageable pageable);
}