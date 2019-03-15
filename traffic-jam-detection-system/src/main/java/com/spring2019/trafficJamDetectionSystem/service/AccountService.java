package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AccountService {
    Account getAccountByUsername(String username, String password);
    boolean createNewAccount(Account entity);
    Page<Account> getAllAccount(Pageable pageable);
    Account getAccountByUsernameAndIsAdmin(String username, String password);
    void updateAccount(Account account);
    List<Account> getAllAccount();
}
