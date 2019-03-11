package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.ImageController;
import com.spring2019.trafficJamDetectionSystem.entity.Image;
import com.spring2019.trafficJamDetectionSystem.model.ImageModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.ImageService;
import com.spring2019.trafficJamDetectionSystem.transformer.ImageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ImageControllerImpl extends AbstractController implements ImageController {


    @Autowired
    ImageService imageService;
    @Autowired
    ImageTransformer imageTransformer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageControllerImpl.class);
    @Override
    public String loadImageByCameraID(Integer id) {
        Response<ImageModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start image with camera id :" + id);
            Image image = imageService.getImageByCameraID(id);
            ImageModel data=imageTransformer.entityToModel(image);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End image with camera id" + id);
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
