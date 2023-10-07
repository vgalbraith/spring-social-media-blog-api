package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Query to retrieve an account given a username.
     * @param username
     * @return The matching account.
     */
    Account findAccountByUsername(String username);
}
