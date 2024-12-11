package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link EventBooking} entities.
 * Provides CRUD operations and custom queries for event bookings.
 */
@Repository
public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {

    /**
     * Retrieves a list of event bookings scheduled for a specific date and time.
     *
     * @param eventDate the date and time of the event
     * @return a list of event bookings matching the specified event date
     */
    List<EventBooking> findByEventDate(LocalDateTime eventDate);
}
