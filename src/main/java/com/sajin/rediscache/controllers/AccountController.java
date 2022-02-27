package com.sajin.rediscache.controllers;

import com.sajin.rediscache.entities.Account;
import com.sajin.rediscache.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController extends ExceptionAwarenessController {

    @Autowired
    private AccountService service;

    @ResponseBody
    @Cacheable(value = "accounts", key = "#id")
    @GetMapping(value = "/{id}")
    public Account getAccount(@PathVariable Integer id) {
        return service.getAccount(id);
    }

    @ResponseBody
    @GetMapping
    @Cacheable(value = "allAccounts", unless= "#result.size() == 0")
    public List<Account> getAllAccounts() {
      return service.getAllAccounts();
    }

    @ResponseBody
    @PostMapping
    @Caching(
            evict= { @CacheEvict(value= "allAccounts", allEntries= true) }
    )
    public Account newAccount(@RequestBody Account account) {
        return service.addAccount(account);
    }

    @ResponseBody
    @PutMapping
    @Caching(
            put= { @CachePut(value= "accounts", key= "#id") },
            evict= { @CacheEvict(value= "allAccounts", allEntries= true) }
    )
    public Account updateAccount(@RequestBody Account newAccount) {
        return service.updateAccount(newAccount);
    }

    @ResponseBody
    @CacheEvict(value = "accounts", key = "#id")
    @DeleteMapping
    @Caching(
            evict= {
                    @CacheEvict(value= "accounts", key= "#id"),
                    @CacheEvict(value= "allAccounts", allEntries= true)
            }
    )
    public Account deleteAccount(Account account) {
        return service.deleteAccount(account);
    }

}
