package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.google.maps.model.LatLng;
import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.repository.CameraRepository;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.utils.PolyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CameraServiceImpl implements CameraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraServiceImpl.class);

    @Autowired
    CameraRepository cameraRepository;

    @Override
    public Camera getActiveCameraById(int id) {
        Optional<Camera> camera = cameraRepository.getByIdAndIsActive(id, true);
        return camera.orElse(null);
    }

    @Override
    public Camera getCameraById(int id) {
        return cameraRepository.findById(id);
    }

    @Override
    public Page<Camera> getAllCameras(Pageable pageable) {
        return cameraRepository.findAllByIsActive(true, pageable);
    }

    @Override
    public List<Camera> getAllCameras() {
        return cameraRepository.findAll();
    }

    @Override
    public List<Camera> getCamerasByStreetAndIsActive(Integer streetId) {
        Street street = new Street();
        street.setId(streetId);
        return cameraRepository.findByStreetByStreetIdAndIsActive(street, true);
    }

    @Override
    public List<Camera> getCameraByStreetNameAndIsActive(String streetName) {
        return cameraRepository.findCameraByStreetName(streetName);
    }


    @Override
    public Camera createCamera(Camera camera) {
        camera.setIsActive(true);
        camera.setObservedStatus(CoreConstant.STATUS_CAMERA_CLEAR);
        return cameraRepository.save(camera);
    }

    @Override
    public Camera updateCamera(Camera camera) {
      return   cameraRepository.save(camera);
    }

    @Override
    public boolean checkCameraOnroute(Bookmark bookmark, Camera camera) {

        String[] coor = bookmark.getRoute_points().split("-");

        ArrayList<LatLng> points = new ArrayList<>();

        for (int i = 0; i < coor.length; i++) {
            String[] point = coor[i].split(",");

            double lat = Double.parseDouble(point[0]);
            double lng = Double.parseDouble(point[1]);
            LatLng position = new LatLng(lat, lng);
            points.add(position);
        }


        if (!points.isEmpty()) {
            String[] xPosArr = camera.getPosition().split(",");
            LatLng xPos = new LatLng(Double.parseDouble(xPosArr[0]), Double.parseDouble(xPosArr[1]));
            if (PolyUtil.isLocationOnPath(xPos, points, false, 10)) {
                LOGGER.info("Camera is on " + bookmark.getOrigin() + "-" + bookmark.getDestination() + " route");
                return true;
            } else {
                LOGGER.info("Camera is not on " + bookmark.getOrigin() + "-" + bookmark.getDestination() + " route");
                return false;
            }
        }

        return false;

    }
}
