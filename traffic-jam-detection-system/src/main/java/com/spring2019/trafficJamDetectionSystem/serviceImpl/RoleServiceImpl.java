package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Role;
import com.spring2019.trafficJamDetectionSystem.repository.RoleRepository;
import com.spring2019.trafficJamDetectionSystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;


    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
