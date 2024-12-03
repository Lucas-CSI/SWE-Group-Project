package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.repository.AccountRepository;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for managing reservations in the SeaSide Escape application.
 * Provides functionality for creating, booking, and managing reservations and their associated rooms.
 */
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BillingService billingService;
    private final RoomService roomService;
    private final AccountService accountService;
    private final BookingService bookingService;
    private final AccountRepository accountRepository;
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    /**
     * Constructs a new {@code ReservationService}.
     *
     * @param reservationRepository the repository for managing reservations
     * @param billingService the service for managing billing operations
     * @param roomService the service for managing rooms
     * @param accountService the service for managing accounts
     * @param bookingService the service for managing bookings
     * @param accountRepository the repository for managing account entities
     */
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService, AccountService accountService, BookingService bookingService, AccountRepository accountRepository) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
        this.bookingService = bookingService;
        this.accountRepository = accountRepository;
    }

    /**
     * Books a reservation for a given user.
     * Marks the user's unbooked reservation as booked and saves the updated information.
     *
     * @param username the username of the account making the reservation
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the booking process
     */
    @Transactional
    public ResponseEntity<String> bookReservation(String username) throws Exception {
        Optional<Account> optionalAccount = accountService.findAccountByUsername(username);
        Account account;
        Reservation reservation;

        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            if (account.getUnbookedReservation() != null) {
                reservation = account.getUnbookedReservation();
                reservation.setBooked(true);
                reservationRepository.save(reservation);
                account.addReservation(reservation);
                account.setUnbookedReservation(null);
                accountService.saveAccount(account);
            } else
                return ResponseEntity.badRequest().body("No reservation to book.");
        }else{
            return ResponseEntity.badRequest().body("Account not found");
        }

        return ResponseEntity.ok().body("Reservation booked");
    }

    /**
     * Creates a new reservation for a given user.
     *
     * @param checkInDate the start date of the reservation
     * @param checkOutDate the end date of the reservation
     * @param username the username of the account creating the reservation
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the reservation creation process
     */
    @Transactional
    public ResponseEntity<String> createReservation(LocalDate checkInDate, LocalDate checkOutDate, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Reservation reservation;
        Account accountObject;

        if(account.isPresent()) {
            accountObject = account.get();
            reservation = new Reservation();
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);
            reservation.setBooked(false);
            reservation.setGuest(accountObject);

            accountObject.setUnbookedReservation(reservation);
            reservationRepository.save(reservation);
            accountRepository.save(accountObject);
        }else{
            return ResponseEntity.badRequest().body("Account not found");
        }

        return ResponseEntity.ok().body("Reservation created");
    }

    /**
     * Adds a room to the user's unbooked reservation.
     * Ensures the room is available for the specified check-in and check-out dates.
     *
     * @param room the room to be added to the reservation
     * @param username the username of the account adding the room
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the room addition process
     */
    @Transactional
    public ResponseEntity<String> addRoom(Room room, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject;
        Reservation accountsReservation;

        if(account.isPresent()) {
            accountObject = account.get();
            accountsReservation = accountObject.getUnbookedReservation();
            if (roomService.isRoomAvailable(room, accountsReservation.getCheckInDate(), accountsReservation.getCheckOutDate())) {
                Booking booking = new Booking(accountsReservation, room);
                bookingService.save(booking);
                accountsReservation.addBooking(booking);
                reservationRepository.save(accountsReservation);
                accountRepository.save(accountObject);
            } else {
                return ResponseEntity.badRequest().body("No room available.");
            }
        }else{
            return ResponseEntity.badRequest().body("You must be logged in.");
        }
        return ResponseEntity.ok().body("Room added");
    }
}
