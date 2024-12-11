package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Query("DELETE FROM Payment p WHERE p.reservation.id = :reservationID")
    void deleteAllByReservationID(@Param("reservationID") long reservationID);
}
