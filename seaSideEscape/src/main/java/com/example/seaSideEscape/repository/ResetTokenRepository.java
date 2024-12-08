package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    void deleteByEmail(String email);
}