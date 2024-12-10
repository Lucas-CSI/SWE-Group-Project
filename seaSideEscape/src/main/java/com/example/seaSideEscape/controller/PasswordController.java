package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    private ResetService resetService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        resetService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset email sent.");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        resetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePassword(
            @RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword) {
        return accountService.updatePassword(username, newPassword);
    }
}