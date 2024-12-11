package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link ResetToken} entities.
 * Provides methods for CRUD operations and custom queries for token management.
 */
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    /**
     * Finds a reset token by its token string.
     *
     * @param token the token string to search for
     * @return an Optional containing the reset token if found, or empty otherwise
     */
    Optional<ResetToken> findByToken(String token);

    /**
     * Deletes all reset tokens associated with the specified email.
     *
     * @param email the email address for which to delete reset tokens
     */
    void deleteByEmail(String email);
}
