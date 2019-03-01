package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.repository.StreetRepository;
import com.spring2019.trafficJamDetectionSystem.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    StreetRepository streetRepository;

    @Override
    public Street getStreetById(int id) {
        Optional<Street> street = streetRepository.findById(id);
        return street.orElse(null);
    }

    @Override
    public Page<Street> getStreetByDistrict(String district, Pageable pageable) {
        return (Page<Street>) streetRepository.findByDistrictAndIsActive(district,true, pageable);
    }

    @Override
    public Page<Street> getAllStreet(Pageable pageable) {
        return  streetRepository.findAll(pageable);
    }
}
