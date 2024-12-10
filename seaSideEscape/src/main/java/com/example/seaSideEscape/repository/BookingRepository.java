package com.example.seaSideEscape.repository;


import com.example.seaSideEscape.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN Reservation res " +
            "ON b.reservation = res WHERE res.checkInDate <= ?2 AND ?1 <= res.checkOutDate")
    List<Booking> getBookingByCheckInDateCheckOutDate(LocalDate checkInDate, LocalDate checkOutDate);

    Optional<Booking> findByReservation_AccountAndRoom(Account account, Room room);

}
