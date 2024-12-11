package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Hotel} entities.
 * Provides CRUD operations for hotel data.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Additional custom queries can be added here as needed
}
