package com.spring2019.trafficJamDetectionSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/portal")
public interface AdminController {

    // Return admin login view
    @GetMapping("/login")
    public ModelAndView openLogin(HttpSession session);

    // Return admin camera page view
    @GetMapping("/camera")
    public ModelAndView openIndex(HttpSession session);

    // Return admin street page view
    @GetMapping("/street")
    public ModelAndView openStreet(HttpSession session);

    // Return admin logout view
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session);
}
