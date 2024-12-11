package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles password-related operations in the SeaSide Escape application.
 * The PasswordController provides endpoints for requesting password resets, resetting passwords,
 * and updating user passwords.
 */
@RestController
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    private ResetService resetService;

    @Autowired
    private AccountService accountService;

    /**
     * Initiates a password reset request by sending a reset email to the specified address.
     *
     * @param email the email address associated with the user account.
     * @return a {@link ResponseEntity} containing a confirmation message.
     */
    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        resetService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset email sent.");
    }

    /**
     * Resets the user's password using a provided token.
     *
     * @param token       the token associated with the password reset request.
     * @param newPassword the new password to set for the user account.
     * @return a {@link ResponseEntity} containing a success message.
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        resetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }

    /**
     * Updates the user's password directly when the username and new password are provided.
     *
     * @param username    the username of the account to update.
     * @param newPassword the new password to set for the account.
     * @return a {@link ResponseEntity} indicating the success or failure of the password update.
     */
    @PostMapping("/update")
    public ResponseEntity<String> updatePassword(
            @RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword) {
        return accountService.updatePassword(username, newPassword);
    }
}
