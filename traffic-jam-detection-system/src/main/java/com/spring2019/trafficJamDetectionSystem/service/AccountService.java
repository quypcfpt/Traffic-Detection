package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AccountService {
    Account getAccountByUsername(String username, String password);
    boolean createNewAccount(Account entity);
}
