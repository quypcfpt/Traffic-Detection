package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface CameraController {

    // Get camera detail by Id
    @GetMapping(CoreConstant.API_CAMERA + "/{id}")
    public String loadCameraById(@PathVariable("id") Integer id);

    // Get all camera
    @GetMapping(CoreConstant.API_CAMERA)
    public String loadAllCameras(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                 @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                 @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy);

    // Get camera by street Id
    @GetMapping(CoreConstant.API_CAMERA + "/streetId/{streetId}")
    public String loadCamerasByStreet(@PathVariable("streetId") Integer streetId);

    // Create new camera
    @PostMapping(CoreConstant.API_CAMERA)
    public String createCamera(@RequestParam String cameraModelString);

    // Update camera
    @PutMapping(CoreConstant.API_CAMERA)
    public String updateCamera(@RequestParam String cameraModelString);

    // Get camera by street name
    @GetMapping(CoreConstant.API_CAMERA + "/streetName/{streetName}")
    public String loadCamerasByStreetNameAndIsActive(@PathVariable("streetName") String streetName);

}


