package com.midasin.jta.service;

import com.midasin.jta.db.generate.model.Account;
import com.midasin.jta.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<String> getAccountList() {
        return accountRepository.findAll().stream().map(Account::getUsername).collect(Collectors.toList());
    }

    public boolean createAccount(String name) {
        Account account = new Account();
        account.setUsername(name);

        return accountRepository.create(account);
    }
}
