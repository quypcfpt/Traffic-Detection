package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import org.springframework.stereotype.Service;


@Service
public interface AccountService {
    Account getAccountByUsername(String username, String password);
    boolean createNewAccount(Account entity);
}
