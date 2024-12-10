package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.AdminPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/changePermission")
    public ResponseEntity<String> changeAccountPermission(@RequestParam("username") String toChangeUsername, @RequestParam("permissionLevel") Integer permissionLevel, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        String response = adminPortalService.modifyPermissionLevel(toChangeUsername, Account.PermissionLevel.values()[permissionLevel], username);

        if(!response.contains("Error")){
            return ResponseEntity.ok("Successfully changed permission");
        }else{
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }
}
