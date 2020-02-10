package com.midasin.sample.controller;

import com.midasin.sample.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/account/insert/{name}")
    public Boolean createAccount(@PathVariable("name") String name) {
        return accountService.createAccount(name);
    }

    @GetMapping("/account/list")
    public List<String> getAccountList() {
        return accountService.getAccountList();
    }
}
