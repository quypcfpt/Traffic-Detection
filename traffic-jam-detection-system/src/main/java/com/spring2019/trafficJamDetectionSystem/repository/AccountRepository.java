package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsernameAndPassword(String username, String password);

    Account findByUsernameAndPasswordAndRoleId(String username, String password, Integer roleId);

    Optional<Account> findByUsername(String username);

    Account findAccountById(Integer id);
}
