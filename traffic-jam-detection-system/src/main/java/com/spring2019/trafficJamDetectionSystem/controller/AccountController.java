package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public interface AccountController {
    @PostMapping(CoreConstant.API_ACCOUNT+"/checkLogin")
    public String checkLogin (@RequestParam (name = "accountModel") String accountModel);

    @PostMapping(CoreConstant.API_ACCOUNT+"/createNewAccount")
    public String createNewAccount (@RequestParam (name = "accountModel")  String accountModel);

    @GetMapping(CoreConstant.API_ACCOUNT)
    public String loadAllAccount (@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "12") Integer size,
                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                  @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy);
    @PostMapping(CoreConstant.API_ACCOUNT+"/admin")
    @ResponseBody
    public String checkAdminLogin (@RequestParam (name = "accountModel") String accountModel, HttpSession session);

    @PutMapping(CoreConstant.API_ACCOUNT+"/role")
    public String changeAccountRole (@RequestParam String accountModelString);

}
