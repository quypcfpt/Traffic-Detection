package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.repository.AccountRepository;
import com.spring2019.trafficJamDetectionSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccountByUsername(String username,String password) {
        Account account=accountRepository.findByUsernameAndPassword(username,password);
        return account;
    }

    @Override
    public boolean createNewAccount(Account entity) {
        Optional<Account> account=accountRepository.findByUsername(entity.getUsername());
        boolean isExistedUsername = account.isPresent();
        if(!isExistedUsername){
            entity.setRoleId(2);
            accountRepository.save(entity);
            return true;
        }
        return false;
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
