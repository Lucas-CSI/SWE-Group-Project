package com.example.seaSideEscape.repository;


import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}

