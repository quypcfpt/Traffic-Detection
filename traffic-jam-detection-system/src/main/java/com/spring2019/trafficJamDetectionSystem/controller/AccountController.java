package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public interface AccountController {
    // Check login for member
    @PostMapping(CoreConstant.API_ACCOUNT+"/checkLogin")
    public String checkLogin (@RequestParam (name = "accountModel") String accountModel);

    // Create new member account
    @PostMapping(CoreConstant.API_ACCOUNT+"/createNewAccount")
    public String createNewAccount (@RequestParam (name = "accountModel")  String accountModel);

    // Check login for admin
    @PostMapping(CoreConstant.API_ACCOUNT+"/admin")
    @ResponseBody
    public String checkAdminLogin (@RequestParam (name = "accountModel") String accountModel, HttpSession session);
}
