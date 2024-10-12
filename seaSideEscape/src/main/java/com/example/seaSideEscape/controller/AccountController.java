package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.model.Account;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

// Change to RequestMapping????
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

    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestBody Account account) throws NoSuchAlgorithmException {
        Optional<Account> acc = accountService.canLogin(account);
        if(acc.isPresent()){
            response.addCookie(new Cookie("username", account.getUsername()));
            response.addCookie(new Cookie("password", acc.get().getPassword()));
        }
        return "done";
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
