package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.CameraController;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import com.spring2019.trafficJamDetectionSystem.model.MultiCameraModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.service.StreetService;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CameraControllerImpl extends AbstractController implements CameraController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraControllerImpl.class);

    @Autowired
    CameraService cameraService;

    @Autowired
    StreetService streetService;

    @Autowired
    CameraTransformer cameraTransformer;

    @Override
    public String loadCameraById(Integer id) {
        Response<CameraModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            LOGGER.info("Start camera" + id);

            Camera camera = cameraService.getCameraById(id);
            CameraModel data = cameraTransformer.entityToModel(camera);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End camera" + id);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String loadAllCameras(Integer page, Integer size, String sort, String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }

        Pageable pageable = null;
        if (page > 0) {
            pageable = PageRequest.of(page - 1, size, sortable);
        }

        Response<MultiCameraModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        LOGGER.info("Start load all cameras");
        try {
            MultiCameraModel data = new MultiCameraModel();
            List<CameraModel> cameraList = new ArrayList<>();
            if (page > 0) {
                Page<Camera> cameras = cameraService.getAllCameras(pageable);
                for (Camera camera : cameras) {
                    cameraList.add(cameraTransformer.entityToModel(camera));
                }
                data.setCurrentPage(page);
                data.setTotalPage(cameras.getTotalPages());
                data.setTotalRecord(cameras.getTotalElements());
            } else {
                List<Camera> cameras = cameraService.getAllCameras();
                for (Camera camera : cameras) {
                    cameraList.add(cameraTransformer.entityToModel(camera));
                }
            }
            data.setCameraList(cameraList);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load all cameras");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);

    }

    @Override
    public String loadCamerasByStreet(Integer streetId, Integer page, Integer size, String sort, String sortBy) {

        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiCameraModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        LOGGER.info("Start load cameras by street with ID: " + streetId);

        try {
            MultiCameraModel data = new MultiCameraModel();
            List<CameraModel> cameraList = new ArrayList<>();
            Page<Camera> cameras = cameraService.getCamerasByStreet(streetId, pageable);

            if (cameras.getSize() == 0) {
                LOGGER.info("Empty result!");
            }

            for (Camera camera : cameras) {
                cameraList.add(cameraTransformer.entityToModel(camera));
            }
            data.setCurrentPage(page);
            data.setTotalPage(cameras.getTotalPages());
            data.setTotalRecord(cameras.getTotalElements());
            data.setCameraList(cameraList);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load street with ID: " + streetId);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String createCamera(String cameraModelString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start create camera: " + cameraModelString);

            CameraModel newCamera = gson.fromJson(cameraModelString, CameraModel.class);
            Camera camera = cameraTransformer.modelToEntity(newCamera);
            cameraService.createCamera(camera);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
            LOGGER.info("End create camera");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String updateCamera(String cameraModelString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            CameraModel cameraModel = gson.fromJson(cameraModelString, CameraModel.class);
            Camera cameraEntity = cameraTransformer.modelToEntity(cameraModel);
            cameraService.updateCamera(cameraEntity);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, true);
            LOGGER.info("Camera updated: " + cameraModelString);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
