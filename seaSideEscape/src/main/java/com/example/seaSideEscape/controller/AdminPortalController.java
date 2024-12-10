package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminPortalController {
    private final AccountService accountService;

    @Autowired
    public AdminPortalController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount/clerk")
    public void createClerkAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username) {
        //accountService.
    }
}
