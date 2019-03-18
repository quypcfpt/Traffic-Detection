package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface CameraController {

    @GetMapping(CoreConstant.API_CAMERA + "/{id}")
    public String loadCameraById(@PathVariable("id") Integer id);

    @GetMapping(CoreConstant.API_CAMERA)
    public String loadAllCameras(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                 @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                 @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy);

    @GetMapping(CoreConstant.API_CAMERA + "/streetId/{streetId}")
    public String loadCamerasByStreet(@PathVariable("streetId") Integer streetId);

    @PostMapping(CoreConstant.API_CAMERA)
    public String createCamera(@RequestParam String cameraModelString);

    @PutMapping(CoreConstant.API_CAMERA)
    public String updateCamera(@RequestParam String cameraModelString);


}


