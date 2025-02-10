package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;

import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private MessageService messageService;

    @Autowired
    public AccountService(AccountRepository accountRepository, MessageService messageService) {
        this.accountRepository = accountRepository;
        this.messageService = messageService;
    }

    public void registerNewUser(Account account) {
        accountRepository.save(account);
    }

    //logs in a user if the username and password combination are valid
    public Account login(String username, String password) {
        
        if(accountRepository.findByUsernameAndPassword(username, password) == null) {
           return null; 
        } 
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    //checks to see if a username is associated with an account or not
    //returns an account object of the account with the username if it exists, null otherwise
    public Account findByUsername(String username) {
        if (accountRepository.findByUsername(username) != null) {
            return accountRepository.findByUsername(username);
        }
        return null;
    }

    //checks to see if a password is associated with an account or not
    //returns an account object of the account with the password if it exists, null otherwise
    public Account findByPassword(String password) {
        if (accountRepository.findByPassword(password) != null) {
            return accountRepository.findByUsername(password);
        }
        return null;
    }

    //checks to see if a accountId is associated with an account or not
    //returns an account object of the account with the accountId if it exists, null otherwise
    public Account findByAccountId(int accountId) {
        if (accountRepository.findById(accountId) == null) {
            return null;
        } else {
            return accountRepository.findById(accountId);
                }
    }
}
