package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface StreetController {

    @GetMapping(CoreConstant.API_STREET + "/{district}")
    public String loadStreetByDistrict(@PathVariable("district") String district,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                       @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy);
    @GetMapping(CoreConstant.API_STREET)
    public String loadAllStreet(       @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(name = "size", required = false, defaultValue = "12") Integer size,
                                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                       @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy);
}
