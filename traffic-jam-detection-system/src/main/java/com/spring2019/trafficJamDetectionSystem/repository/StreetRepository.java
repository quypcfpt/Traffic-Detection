package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street,Integer> {

    Page<Street> findByDistrictAndIsActive(String district,boolean isActive, Pageable pageable);

    Page<Street> findByNameContainingIgnoreCaseAndIsActive (String district,Boolean isActive ,Pageable pageable);
}


