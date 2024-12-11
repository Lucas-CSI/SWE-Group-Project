package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Payment} entities.
 * Provides methods for CRUD operations and custom queries related to payments.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Retrieves all payments associated with a specific reservation ID.
     *
     * @param reservationId the ID of the reservation
     * @return a list of payments associated with the given reservation ID
     */
    List<Payment> findByReservationId(Long reservationId);

    /**
     * Deletes all payments associated with a specific reservation ID.
     *
     * @param reservationId the ID of the reservation
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Payment p WHERE p.reservation.id = :reservationId")
    void deleteAllByReservationId(@Param("reservationId") Long reservationId);
}
