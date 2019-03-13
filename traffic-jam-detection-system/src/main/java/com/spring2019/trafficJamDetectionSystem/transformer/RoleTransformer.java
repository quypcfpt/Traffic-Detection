package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Role;
import com.spring2019.trafficJamDetectionSystem.model.RoleModel;
import org.springframework.stereotype.Service;

@Service
public interface RoleTransformer {
    public RoleModel entityToModel(Role entity);

    public Role modelToEntity(RoleModel model);

}
