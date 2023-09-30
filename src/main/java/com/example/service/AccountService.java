package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ConflictException;
import com.example.exception.BadRequestException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Given a brand new transient account (meaning, no such account exists yet in the database),
     * persist the account to the database (create a new database record for the account entity.)
     */
    public Account persistAccount(Account account) throws Exception {
    	if (account.getUsername().equals("")) {
    		throw new BadRequestException("Username cannot be blank.");
        }
    	
        if (account.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters long.");
        }
    
        List<Account> accounts = accountRepository.findAll();
        for (Account a : accounts) {
            if (a.getUsername().equals(account.getUsername())) {
                throw new ConflictException("Username already exists.");
            }
        }
        return accountRepository.save(account);
    }
}
