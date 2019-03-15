package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.controller.AdminController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AdminControllerImpl extends AbstractController implements AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminControllerImpl.class);
    @Override
    public ModelAndView openLogin(HttpSession session) {
        String username = (String)session.getAttribute("username");
        if(username != null){
            return new ModelAndView("redirect:/portal/test");
        }else{
            return new ModelAndView("login");
        }
    }

    @Override
    public ModelAndView openIndex(HttpSession session)  {
        String username = (String)session.getAttribute("username");
        if(username != null){
            return new ModelAndView("tables");
        }else{
            return new ModelAndView("redirect:/portal/login");
        }
    }

    @Override
    public ModelAndView openStreet(HttpSession session)  {
        String username = (String)session.getAttribute("username");
        if(username != null){
            return new ModelAndView("street_tables");
        }else{
            return new ModelAndView("redirect:/portal/login");
        }
    }

    @Override
    public ModelAndView openIndex() {
        return new ModelAndView("tables");
    }


    public ModelAndView openTest() {
        return new ModelAndView("blank");
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("username");
        LOGGER.info("Admin account logged out");
        return new ModelAndView("redirect:/portal/login");
    }
}
