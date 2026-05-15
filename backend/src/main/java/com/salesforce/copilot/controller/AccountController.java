package com.salesforce.copilot.controller;

import com.salesforce.copilot.model.Account;
import com.salesforce.copilot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // GET http://localhost:8080/api/accounts
    // Returns all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // GET http://localhost:8080/api/accounts/1
    // Returns one account by ID
    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    // POST http://localhost:8080/api/accounts
    // Creates a new account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }
}