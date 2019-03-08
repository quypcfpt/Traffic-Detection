package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    boolean getAccountByUsername(String username,String password);
    boolean createNewAccount(Account entity);
}
