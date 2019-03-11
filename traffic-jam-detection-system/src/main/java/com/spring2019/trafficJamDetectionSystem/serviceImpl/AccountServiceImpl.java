package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.repository.AccountRepository;
import com.spring2019.trafficJamDetectionSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccountByUsername(String username,String password) {
        Account account=accountRepository.findByUsernameAndPasswordAndStatus(username,password,true);
        return account;
    }

    @Override
    public boolean createNewAccount(Account entity) {
        Optional<Account> account=accountRepository.findByUsernameAndStatus(entity.getUsername(),true);
        boolean isExistedUsername = account.isPresent();
        if(!isExistedUsername){
            entity.setStatus(true);
            entity.setRoleId(2);
            accountRepository.save(entity);
            return true;
        }
        return false;
    }
}
