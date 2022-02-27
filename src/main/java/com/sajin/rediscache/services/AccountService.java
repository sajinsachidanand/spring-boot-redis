package com.sajin.rediscache.services;

import com.sajin.rediscache.entities.Account;
import com.sajin.rediscache.exceptions.ElementNotFound;
import com.sajin.rediscache.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account getAccount(Integer id) {
        Account account = repository.findById(id).get();
        if (account == null) {
            throw new ElementNotFound("No Account With Id" + id + " Found");
        }
        return account;
    }

    public Account addAccount(Account account) {
        return repository.save(account);
    }

    public Account deleteAccount(Account account) {
        repository.delete(account);
        return account;
    }

    public Account updateAccount(Account account) {
        return repository.save(account);
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) repository.findAll();
    }

}
