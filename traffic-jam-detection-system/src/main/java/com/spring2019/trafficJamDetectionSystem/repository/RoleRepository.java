package com.spring2019.trafficJamDetectionSystem.repository;


import com.spring2019.trafficJamDetectionSystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
