package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.AccountController;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.AccountService;
import com.spring2019.trafficJamDetectionSystem.transformer.AccountTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AccountControllerImpl extends AbstractController implements AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountControllerImpl.class);
    @Autowired
    AccountService accountService;
    @Autowired
    AccountTransformer accountTransformer;
    @Override
    public String checkLogin(String accountModel) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start create camera: " + accountModel);

            AccountModel accoutModel = gson.fromJson(accountModel, AccountModel.class);
            Account accountEntity=accountTransformer.modelToEntity(accoutModel);
            Account account = accountService.getAccountByUsername(accountEntity.getUsername(),accountEntity.getPassword());
            AccountModel data = accountTransformer.entityToModel(account);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS,data);
            LOGGER.info("End create camera");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);

    }

    @Override
    public String createNewAccount(String accountModel) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start create camera: " + accountModel);

            AccountModel accoutModel = gson.fromJson(accountModel, AccountModel.class);
            Account accountEntity=accountTransformer.modelToEntity(accoutModel);
            boolean isSuccess =accountService.createNewAccount(accountEntity);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS,isSuccess);
            LOGGER.info("End create camera");
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
