package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface ReportController {

    // Get camera detail by Id
    @GetMapping(CoreConstant.API_REPORT + "/{id}/{date}")
    public String loadReportByCameraAndDate(@PathVariable("id") Integer id, @PathVariable("date") String date);
}
