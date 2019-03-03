package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CameraService {
    public Camera getCameraById(int id);

    public Page<Camera> getAllCameras(Pageable pageable);

    public Page<Camera> getCamerasByStreet(Integer street, Pageable pageable);

    public void createCamera(Camera camera);

    public void updateCamera(Camera camera);
}
