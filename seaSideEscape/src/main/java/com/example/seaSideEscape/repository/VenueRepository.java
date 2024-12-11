package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Venue} entities.
 * Provides methods for CRUD operations and custom queries.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    /**
     * Finds venues based on their booking status and floor number.
     *
     * @param isBooked   the booking status of the venue (true if booked, false otherwise)
     * @param floorNumber the floor number to filter venues
     * @return a list of venues matching the specified booking status and floor number
     */
    List<Venue> findByIsBookedAndFloorNumber(boolean isBooked, int floorNumber);
}
