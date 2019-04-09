package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CameraService {
    public Camera getCameraById(int id);

    public Page<Camera> getAllCameras(Pageable pageable);

    public Camera createCamera(Camera camera);

    public void updateCamera(Camera camera);

    public List<Camera> getAllCameras();

    public List<Camera> getCamerasByStreetAndIsActive(Integer street);

    public List<Camera>  getCameraByStreetNameAndIsActive(String streetName);

    public boolean checkCameraOnroute(Bookmark bookmark, Camera camera);


}
