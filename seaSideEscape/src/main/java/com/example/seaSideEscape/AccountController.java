package com.example.seaSideEscape;

import com.example.seaSideEscape.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) throws NoSuchAlgorithmException {
        return accountService.createAccount(account);
    }

    @GetMapping("/createAccount")
    public String createAccountPage(){
        return "create acccount";
    }
}
