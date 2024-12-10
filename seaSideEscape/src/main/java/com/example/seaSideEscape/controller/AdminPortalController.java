package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/admin")
public class AdminPortalController {
    private final AdminPortalService adminPortalService;

    @Autowired
    public AdminPortalController(AdminPortalService adminPortalService) {
        this.adminPortalService = adminPortalService;
    }

    @PostMapping("/createAccount/clerk")
    public ResponseEntity<String> createClerkAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Clerk);
    }

    @PostMapping("/createAccount/admin")
    public ResponseEntity<String> createAdminAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Admin);
    }
}
