package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByReservationId(Long reservationId);

    @Modifying
    @Query("DELETE FROM Payment p WHERE p.reservation.id = :reservationId")
    void deleteAllByReservationId(@Param("reservationId") Long reservationId);

}
