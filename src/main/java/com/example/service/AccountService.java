package com.example.service;

import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.example.entity.Account;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;

@Service
@Component
@ComponentScan(basePackages = "com.example.repository")
public class AccountService {
    AccountRepository repository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.repository = accountRepository;
    }
    public Account register(Account account) {
        if (!account.getUsername().isEmpty()) {
            if (account.getPassword().length() > 3) {
                if (repository.findUsername(account.getUsername()).isEmpty()) {
                    return repository.save(account);
                }
            }
        }
        return null;
    }
    public boolean checkUsername(Account account) {
        return repository.findUsername(account.getUsername()).isEmpty();
    }
    public Account login(Account account) {
        List<Account> list = repository.findLogin(account.getUsername(), account.getPassword());
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
