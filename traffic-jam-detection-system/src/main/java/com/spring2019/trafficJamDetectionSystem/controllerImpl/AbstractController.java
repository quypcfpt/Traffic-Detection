package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class AbstractController {

    protected  final Gson gson;

    public AbstractController() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

}
