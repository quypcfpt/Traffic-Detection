package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.AccountController;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
>>>>>>> master
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.AccountService;
import com.spring2019.trafficJamDetectionSystem.transformer.AccountTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
            LOGGER.info("Start Check login: " + accountModel);
            AccountModel accoutModel = gson.fromJson(accountModel, AccountModel.class);
            Account accountEntity=accountTransformer.modelToEntity(accoutModel);
            Account account = accountService.getAccountByUsername(accountEntity.getUsername(),accountEntity.getPassword());
            AccountModel data = accountTransformer.entityToModel(account);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS,data);
            LOGGER.info("End Check login: " + accountModel);
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


    @Override
    public String loadAllAccount(Integer page, Integer size, String sort, String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = null;
        if(page > 0){
            pageable = PageRequest.of(page - 1, size, sortable);
        }

        Response<MultiAccountModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        LOGGER.info("Start load all accounts");
        try {
            MultiAccountModel data = new MultiAccountModel();
            List<AccountModel> accountList = new ArrayList<>();
            if (page > 0) {
                Page<Account> accounts = accountService.getAllAccount(pageable);
                if (accounts.getSize() == 0) {
                    LOGGER.info("Empty result!");
                }

                for (Account account : accounts) {
                    accountList.add(accountTransformer.entityToModel(account));
                }
                data.setCurrentPage(page);
                data.setTotalPage(accounts.getTotalPages());
                data.setTotalRecord(accounts.getTotalElements());
            }else {
                List<Account> accounts = accountService.getAllAccount();
                for (Account account : accounts) {
                    accountList.add(accountTransformer.entityToModel(account));
                }
                data.setAccountList(accountList);

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
                LOGGER.info("End load all accounts");
            }
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    public String changeAccountRole(String accountModelString) {
        Response response = new Response(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            AccountModel accountModel = gson.fromJson(accountModelString, AccountModel.class);
            Account accountEntity = accountTransformer.modelToEntity(accountModel);
            accountService.updateAccount(accountEntity);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, accountModel);
            LOGGER.info("Account role updated");
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
    @Override
    public String checkAdminLogin(String accountModel, HttpSession session) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Check admin login: " + accountModel);

            AccountModel accoutModel = gson.fromJson(accountModel, AccountModel.class);
            Account accountEntity=accountTransformer.modelToEntity(accoutModel);
            Account account = accountService.getAccountByUsernameAndIsAdmin(accountEntity.getUsername(),accountEntity.getPassword());
            if(account != null){
                LOGGER.info("Admin account is authenticated");
                session.setAttribute("username", account.getUsername());
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, "success");
            }else {
                LOGGER.info("Admin account not found");
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, "failed");
            }
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);

    }
}

