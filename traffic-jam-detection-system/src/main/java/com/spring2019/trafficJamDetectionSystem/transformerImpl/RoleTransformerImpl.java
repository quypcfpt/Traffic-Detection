package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Role;
import com.spring2019.trafficJamDetectionSystem.model.AccountModel;
import com.spring2019.trafficJamDetectionSystem.model.RoleModel;
import com.spring2019.trafficJamDetectionSystem.transformer.RoleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleTransformerImpl implements RoleTransformer {
    @Autowired
    RoleTransformer roleTransformer;
    @Override
    public RoleModel entityToModel(Role entity) {
        RoleModel model = new RoleModel();

        model.setId(entity.getId());
        model.setRole(entity.getRole());
        return model;
    }

    @Override
    public Role modelToEntity(RoleModel model) {
        Role entity = new Role();

        entity.setId(model.getId());
        entity.setRole(model.getRole());
        return entity;
    }
}
