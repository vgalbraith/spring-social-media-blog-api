package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
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
     * Used to persist an account to the repository.
     * @param account The account to be added.
     * @return The persisted account including it's newly assigned account_id.
     * @throws BadRequestException if there's an issue with the client's request.
     * @throws ConflictException if the username is already associated with a registered account.
     */
    public Account persistAccount(Account account) {
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

    /**
     * Used to verify a login.
     * @param account Account object containing the username and password to verify.
     * @return The verified account object.
     * @throws UnauthorizedException if the username and/or password are invalid.
     */
    public Account verifyAccount(Account account) {
        List<Account> accounts = accountRepository.findAll();
        for (Account a : accounts) {
            if (a.getUsername().equals(account.getUsername()) && a.getPassword().equals(account.getPassword())) {
                return a;
            }
        }
        
        throw new UnauthorizedException("Invalid username and/or password.");
    }
}
