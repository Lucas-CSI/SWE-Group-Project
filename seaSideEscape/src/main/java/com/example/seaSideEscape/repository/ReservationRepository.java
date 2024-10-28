package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    List<Reservation> findByRoom();
}

