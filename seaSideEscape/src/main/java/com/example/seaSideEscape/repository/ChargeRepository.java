package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Charge} entities.
 * Provides CRUD operations for charges.
 */
@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {
    // Additional custom queries can be added here as needed
}
