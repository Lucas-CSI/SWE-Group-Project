package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a password reset token in the SeaSide Escape application.
 * The ResetToken class is used to manage temporary tokens for password reset functionality.
 */
@Entity
@Table(name = "reset_token")
public class ResetToken {

    /**
     * The unique identifier for the reset token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique token used for resetting the password.
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The email address associated with the reset token.
     */
    @Column(nullable = false)
    private String email;

    /**
     * The expiration date and time of the reset token.
     * After this time, the token becomes invalid.
     */
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Gets the unique identifier for the reset token.
     *
     * @return the reset token ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the reset token.
     *
     * @param id the new reset token ID.
     */
    public void setID(Long id) {
        this.id = id;
    }

    /**
     * Gets the unique token used for resetting the password.
     *
     * @return the reset token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the unique token used for resetting the password.
     *
     * @param token the new reset token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the email address associated with the reset token.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address associated with the reset token.
     *
     * @param email the new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the expiration date and time of the reset token.
     *
     * @return the expiration date and time.
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiration date and time of the reset token.
     *
     * @param expiryDate the new expiration date and time.
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
