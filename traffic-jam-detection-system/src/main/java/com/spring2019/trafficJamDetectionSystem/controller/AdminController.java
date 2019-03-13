package com.spring2019.trafficJamDetectionSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal")
public interface AdminController {
    @GetMapping("/login")
    public ModelAndView openLogin();

    @GetMapping("/test")
    public ModelAndView openTest();
}
