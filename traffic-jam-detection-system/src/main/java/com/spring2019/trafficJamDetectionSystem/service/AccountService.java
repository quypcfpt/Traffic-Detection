package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface AccountService {
    Account getAccountByUsername(String username, String password);
    boolean createNewAccount(Account entity);
    Page<Account> getAllAccounts(Pageable pageable);
    Account getAccountById(Integer id);
    void updateAccount(Account account);
}
