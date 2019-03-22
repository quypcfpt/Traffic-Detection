package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.DetectionController;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import com.spring2019.trafficJamDetectionSystem.model.ImageModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@RestController
@CrossOrigin
public class DetectionControllerImpl extends AbstractController implements DetectionController {

    @Autowired
    CameraService cameraService;

    @Autowired
    CameraTransformer cameraTransformer;


    private static final Logger LOGGER = LoggerFactory.getLogger(DetectionControllerImpl.class);
    public static HashMap<Integer, DetectionModel> detectResultData=new HashMap<>();
    @Override
    public String detectResult(String detectResultString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Getting traffic info: " + detectResultString);
            DetectionModel detectionResult = gson.fromJson(detectResultString, DetectionModel.class);
            //Update Camera Status
            CameraModel cameraModel = new CameraModel();
            cameraModel.setId(detectionResult.getCameraId());
            cameraModel.setObserverStatus(detectionResult.getStatusId());

            //Update Image  link and time
            cameraModel.setImgUrl(detectionResult.getImageUrl());

            Date date= new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            cameraModel.setTime(ts.toString());

            Camera camera = cameraTransformer.modelToEntity(cameraModel);
            Camera Newcamera = cameraService.getCameraById(camera.getId());
            Newcamera.setObservedStatus(camera.getObservedStatus());
            cameraService.updateCamera(Newcamera);

            detectResultData.put(detectionResult.getCameraId(), detectionResult);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
            LOGGER.info("End update traffic info");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
