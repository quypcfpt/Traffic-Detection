package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreetService {
    Page<Street> getStreetByDistrict(String district, Pageable pageable);

    Page<Street> getAllStreet(Pageable pageable);

    List<Street> getAllStreet();

    void createStreet(Street street);

    void updateStreet(Street street);

    Page<Street> getStreetBySearch(String txtSearch, Pageable pageable);
}
