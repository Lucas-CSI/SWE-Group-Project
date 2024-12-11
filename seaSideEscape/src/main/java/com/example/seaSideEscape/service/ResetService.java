package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.ResetToken;
import com.example.seaSideEscape.repository.ResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing password reset functionality.
 * Handles generating reset tokens, sending reset emails, and updating passwords.
 */
@Service
public class ResetService {

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountService accountService;

    /**
     * Constructs a new ResetService with the specified dependencies.
     *
     * @param resetTokenRepository the repository for managing reset tokens
     * @param emailService         the service for sending emails
     * @param accountService       the service for managing accounts
     */
    public ResetService(ResetTokenRepository resetTokenRepository, EmailService emailService, AccountService accountService) {
        this.resetTokenRepository = resetTokenRepository;
        this.emailService = emailService;
        this.accountService = accountService;
    }

    /**
     * Initiates a password reset request by generating a reset token, saving it in the repository,
     * and sending a reset email to the user.
     *
     * @param email the email address of the user requesting the password reset
     */
    @Transactional
    public void requestPasswordReset(String email) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        // Remove existing reset tokens for the email
        resetTokenRepository.deleteByEmail(email);

        // Create and save a new reset token
        ResetToken resetToken = new ResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(expiryDate);
        resetTokenRepository.save(resetToken);

        // Generate reset link and send the reset email
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(email, resetLink);
    }

    /**
     * Resets the user's password using the provided reset token.
     * Validates the token and updates the user's password if the token is valid.
     *
     * @param token       the reset token provided by the user
     * @param newPassword the new password to set for the user
     * @throws IllegalArgumentException if the token is invalid or expired
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        Optional<ResetToken> optionalToken = resetTokenRepository.findByToken(token);

        // Validate token existence and expiration
        if (optionalToken.isEmpty() || optionalToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }

        // Update the password for the associated email
        String email = optionalToken.get().getEmail();
        accountService.updatePassword(email, newPassword);

        // Delete the used reset token
        resetTokenRepository.delete(optionalToken.get());
    }
}