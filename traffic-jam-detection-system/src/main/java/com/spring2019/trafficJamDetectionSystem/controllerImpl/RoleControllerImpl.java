package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.RoleController;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Role;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import com.spring2019.trafficJamDetectionSystem.model.MultiStreetModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.model.RoleModel;
import com.spring2019.trafficJamDetectionSystem.service.RoleService;
import com.spring2019.trafficJamDetectionSystem.serviceImpl.RoleServiceImpl;
import com.spring2019.trafficJamDetectionSystem.transformer.RoleTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class RoleControllerImpl extends AbstractController implements RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    RoleService roleService;

    @Autowired
    RoleTransformer roleTransformer;
    @Override
    public String loadAllRole() {
        Response<RoleModel> response = new Response<RoleModel>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start create camera: ");
            RoleModel roleModel = new RoleModel();
            List<RoleModel> models = new ArrayList<RoleModel>();
            List<Role> entities = roleService.getAllRole();
            for(Role entity : entities){
                models.add(roleTransformer.entityToModel(entity));
            }
            roleModel.setList(models);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS,roleModel);
            LOGGER.info("End create camera");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
