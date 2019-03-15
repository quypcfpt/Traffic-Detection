package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.controller.AdminController;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminControllerImpl extends AbstractController implements AdminController {
    @Override
    public ModelAndView openLogin() {
        return new ModelAndView("login");
    }

    @Override
    public ModelAndView openIndex() {
        return new ModelAndView("tables");
    }


    public ModelAndView openTest() {
        return new ModelAndView("blank");
    }
}
