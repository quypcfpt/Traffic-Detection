package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.StreetController;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.MultiStreetModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.model.StreetModel;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.service.StreetService;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import com.spring2019.trafficJamDetectionSystem.transformer.StreetTransformer;
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
public class StreetControllerImpl extends AbstractController implements StreetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreetControllerImpl.class);

    @Autowired
    StreetService streetService;

    @Autowired
    StreetTransformer streetTransformer;

    @Autowired
    CameraService cameraService;

    @Autowired
    CameraTransformer cameraTransformer;


    @Override
    public String loadStreetByDistrict(String district, Integer page, Integer size, String sort, String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiStreetModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            MultiStreetModel data = new MultiStreetModel();

            List<StreetModel> streetList = new ArrayList<>();
            Page<Street> streets = streetService.getStreetByDistrict(district, pageable);


            if (streets.getSize() == 0) {
                LOGGER.info("Empty result!");
            }

            for (Street street : streets) {
                streetList.add(streetTransformer.entityToModel(street));
            }
            data.setCurrentPage(page);
            data.setTotalPage(streets.getTotalPages());
            data.setTotalRecord(streets.getTotalElements());
            data.setStreetList(streetList);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load streets in district " + district);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String loadAllStreet(Integer page, Integer size, String sort, String sortBy) {
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
        Response<MultiStreetModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            MultiStreetModel data = new MultiStreetModel();

            List<StreetModel> streetList = new ArrayList<>();
            if (page > 0) {
                Page<Street> streets = streetService.getAllStreet(pageable);
                if (streets.getSize() == 0) {
                    LOGGER.info("Empty result!");
                }

                for (Street street : streets) {
                    streetList.add(streetTransformer.entityToModel(street));
                }
                data.setCurrentPage(page);
                data.setTotalPage(streets.getTotalPages());
                data.setTotalRecord(streets.getTotalElements());
            }else{
                List<Street> streets=streetService.getAllStreet();
                for (Street street : streets) {
                    streetList.add(streetTransformer.entityToModel(street));
                }
            }
            data.setStreetList(streetList);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load streets  ");
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String loadStreetBySearch(String search,Integer page, Integer size, String sort, String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiStreetModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            MultiStreetModel data = new MultiStreetModel();

            List<StreetModel> streetList = new ArrayList<>();
            Page<Street> streets = streetService.getStreetBySearch(search,pageable);
            if (streets.getSize() == 0) {
                LOGGER.info("Empty result!");
            }

            for (Street street : streets) {
                streetList.add(streetTransformer.entityToModel(street));
            }
            data.setCurrentPage(page);
            data.setTotalPage(streets.getTotalPages());
            data.setTotalRecord(streets.getTotalElements());
            data.setStreetList(streetList);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load streets in district ");
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);

    }

    @Override
    public String createStreet(String streetModelString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start create street: " + streetModelString);
            StreetModel streetModel = gson.fromJson(streetModelString, StreetModel.class);
            Street streetEntity = streetTransformer.modelToEntity(streetModel);
            streetService.createStreet(streetEntity);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, true);
            LOGGER.info("End create street");
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String updateStreet(String streetModelString) {

        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            StreetModel streetModel = gson.fromJson(streetModelString, StreetModel.class);
            Street streetEntity = streetTransformer.modelToEntity(streetModel);
            streetService.updateStreet(streetEntity);
            if(!streetModel.getIsActive()){
                ArrayList<Camera> cameras = (ArrayList)cameraService.getCamerasByStreetAndIsActive(streetModel.getId());
                for (Camera x : cameras) {
                    x.setIsActive(false);
                    cameraService.updateCamera(x);
                }
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, streetModel);
            LOGGER.info("Street updated: " + streetModelString);
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
