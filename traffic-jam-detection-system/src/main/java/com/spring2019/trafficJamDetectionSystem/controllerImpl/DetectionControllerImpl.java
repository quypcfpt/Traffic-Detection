package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.DetectionController;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

import java.util.HashMap;

@RestController
@CrossOrigin
public class DetectionControllerImpl extends AbstractController implements DetectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectionControllerImpl.class);

    public static HashMap<Integer, DetectionModel> detectResultData=new HashMap<>();

    @Override
    public String detectResult(String detectResultString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Getting traffic info: " + detectResultString);

            DetectionModel detectionResult = gson.fromJson(detectResultString, DetectionModel.class);

            detectResultData.put(detectionResult.getCameraId(), detectionResult);

            DetectionModel detectionResult = gson.fromJson(detectResultString, DetectionModel.class);
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
