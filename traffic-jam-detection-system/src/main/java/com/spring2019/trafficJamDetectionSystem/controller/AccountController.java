package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface AccountController {
    @PostMapping(CoreConstant.API_ACCOUNT+"/checkLogin")
    public String checkLogin (@RequestParam (name = "accountModel") String accountModel);

    @PostMapping(CoreConstant.API_ACCOUNT+"/createNewAccount")
    public String createNewAccount (@RequestParam (name = "accountModel")  String accountModel);
}
