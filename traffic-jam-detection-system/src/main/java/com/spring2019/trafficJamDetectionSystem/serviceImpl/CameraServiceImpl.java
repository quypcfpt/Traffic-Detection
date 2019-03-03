package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.repository.CameraRepository;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    CameraRepository cameraRepository;

    @Override
    public Camera getCameraById(int id) {
        Optional<Camera> camera = cameraRepository.getByIdAndIsActive(id,true);
        return camera.orElse(null);
    }

    @Override
    public Page<Camera> getAllCameras(Pageable pageable) {
        return cameraRepository.findAllByIsActive(true,pageable);
    }

    @Override
    public Page<Camera> getCamerasByStreet(Integer street, Pageable pageable) {
        return cameraRepository.findByStreetIdAndIsActive(street,true,pageable);
    }
    @Override
    public void createCamera(Camera camera) {
        camera.setIsActive(true);
        cameraRepository.save(camera);
    }

    @Override
    public void updateCamera(Camera camera) {
        cameraRepository.save(camera);
    }


}
