package com.spring2019.trafficJamDetectionSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/portal")
public interface AdminController {

    @GetMapping("/login")
    public ModelAndView openLogin(HttpSession session);


    @GetMapping("/camera")
    public ModelAndView openIndex(HttpSession session);

    @GetMapping("/street")
    public ModelAndView openStreet(HttpSession session);

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session);
}
