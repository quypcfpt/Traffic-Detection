package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StreetService {

    List<Street> getAllStreet();

    Page<Street> getAllStreetAndIsActive(Pageable pageable);

    void createStreet(Street street);

    void updateStreet(Street street);

    Page<Street> getStreetBySearch(String txtSearch, Pageable pageable);
}
