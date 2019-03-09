package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByUsernameAndPasswordAndStatus(String username,String password , Boolean isActive);
    Optional<Account> findByUsernameAndStatus(String username ,Boolean isActive);
}
