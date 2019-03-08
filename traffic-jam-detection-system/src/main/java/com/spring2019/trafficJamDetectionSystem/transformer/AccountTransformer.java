package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import org.springframework.stereotype.Service;

@Service
public interface AccountTransformer {
    public AccountModel entityToModel(Account entity);

    public Account modelToEntity(AccountModel model);
}
