package com.example.seaSideEscape.repository;


import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN Reservation res " +
            "ON b.reservation = res WHERE res.checkInDate <= ?2 AND ?1 <= res.checkOutDate")
    List<Booking> getBookingByCheckInDateCheckOutDate(LocalDate checkInDate, LocalDate checkOutDate);
}

