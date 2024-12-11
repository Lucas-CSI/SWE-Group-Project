package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Booking} entities.
 * Provides CRUD operations and custom queries for booking management.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Retrieves a list of bookings that overlap with a specified check-in and check-out date range.
     *
     * @param checkInDate  the start date of the range
     * @param checkOutDate the end date of the range
     * @return a list of bookings that overlap with the specified date range
     */
    @Query("SELECT b FROM Booking b JOIN Reservation res " +
            "ON b.reservation = res WHERE res.checkInDate <= ?2 AND ?1 <= res.checkOutDate")
    List<Booking> getBookingByCheckInDateCheckOutDate(LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Finds a booking for a specific account and room.
     *
     * @param account the account associated with the booking
     * @param room    the room associated with the booking
     * @return an Optional containing the booking if found, or empty otherwise
     */
    Optional<Booking> findByReservation_AccountAndRoom(Account account, Room room);
}
