package com.example.seaSideEscape.repository;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Reservation} entities.
 * Provides methods for CRUD operations and custom queries related to reservations.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Retrieves all reservations associated with a specific username.
     *
     * @param username the username of the account
     * @return a list of reservations associated with the given username
     */
    @Query("SELECT res FROM Reservation res JOIN Account acc ON acc = res.account WHERE acc.username = ?1")
    List<Reservation> findAllReservationsByUser(String username);

    /**
     * Finds the first unpaid reservation for a given account.
     *
     * @param account the account to search for unpaid reservations
     * @return an Optional containing the unpaid reservation if found, or empty otherwise
     */
    @Query("SELECT res FROM Reservation res WHERE res.account = ?1 AND NOT res.paid")
    Optional<Reservation> findByAccountAndPaidFalse(Account account);
}
