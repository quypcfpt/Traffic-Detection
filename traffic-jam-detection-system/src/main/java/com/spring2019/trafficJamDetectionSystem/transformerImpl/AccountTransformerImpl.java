package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import com.spring2019.trafficJamDetectionSystem.transformer.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTransformerImpl implements AccountTransformer {
    @Autowired
    AccountTransformer accountTransformer;
    @Override
    public AccountModel entityToModel(Account entity) {
        AccountModel model = new AccountModel();

        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setPassword(entity.getPassword());
        model.setName(entity.getName());
        model.setRoleId(entity.getRoleId());
        return model;
    }

    @Override
    public Account modelToEntity(AccountModel model) {
        Account entity = new Account();

        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setName(model.getName());
        entity.setRoleId(model.getRoleId());
        return entity;
    }
}
