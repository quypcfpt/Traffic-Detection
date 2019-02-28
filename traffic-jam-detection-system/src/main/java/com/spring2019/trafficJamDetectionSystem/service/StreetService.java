package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreetService {

    Street getStreetById(int id);

    Page<Street> getStreetByDistrict(String district, Pageable pageable);
    Page<Street> getAllStreet(Pageable pageable);

}
