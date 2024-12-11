package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.ReservationService;
import com.example.seaSideEscape.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for handling reservation-related requests in the SeaSide Escape application.
 * Provides endpoints for creating, booking, managing reservations, and handling guest check-in/check-out.
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final ReservationRepository reservationRepository;
    private final AccountService accountService;

    /**
     * Constructs a new {@code ReservationController}.
     *
     * @param reservationService   the service handling reservation-related operations.
     * @param roomService          the service handling room-related operations.
     * @param reservationRepository the repository for reservation data.
     * @param accountService       the service handling account-related operations.
     */
    @Autowired
    public ReservationController(ReservationService reservationService, RoomService roomService,
                                 ReservationRepository reservationRepository, AccountService accountService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.reservationRepository = reservationRepository;
        this.accountService = accountService;
    }

    /**
     * Books an existing reservation for the logged-in user.
     *
     * @param username the username of the logged-in user (retrieved from a cookie).
     * @return a {@code ResponseEntity} containing a success or error message.
     * @throws Exception if an error occurs during the booking process.
     */
    @PostMapping("/book")
    public ResponseEntity<String> bookReservation(@CookieValue("username") String username) throws Exception {
        return reservationService.bookReservation(username);
    }

    /**
     * Creates a new reservation for the logged-in user.
     *
     * @param checkInDate  the start date of the reservation, formatted as ISO 8601.
     * @param checkOutDate the end date of the reservation, formatted as ISO 8601.
     * @param username     the username of the logged-in user (retrieved from a cookie).
     * @return a {@code ResponseEntity} containing a success or error message.
     * @throws Exception if an error occurs during the reservation creation process.
     */
    @PostMapping("/new")
    public ResponseEntity<String> createReservation(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @CookieValue("username") String username) throws Exception {
        return reservationService.createReservation(checkInDate, checkOutDate, username);
    }

    /**
     * Adds a room to the user's current reservation.
     *
     * @param room     the room to be added, passed as a JSON object in the request body.
     * @param username the username of the logged-in user (retrieved from a cookie).
     * @return a {@code ResponseEntity} containing a success or error message.
     * @throws Exception if an error occurs during the room addition process.
     */
    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody Room room, @CookieValue("username") String username) throws Exception {
        return reservationService.addRoom(room, username);
    }

    /**
     * Populates the database with initial room data.
     * Intended for administrative use or during development.
     *
     * @throws Exception if an error occurs during the database setup process.
     */
    @PostMapping("/fillDB")
    public void fillDB() throws Exception {
        roomService.setupDB();
    }

    /**
     * Retrieves the user's current reservation.
     *
     * @param username the username of the logged-in user (retrieved from a cookie).
     * @return a {@code ResponseEntity} containing the reservation ID or an error message.
     */
    @GetMapping("/current")
    public ResponseEntity<String> getCurrentReservation(@CookieValue("username") String username) {
        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Account account = accountService.findAccountByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));

            String reservation = String.valueOf(reservationService.getUnpaidReservation(account).getId());
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Checks in a guest based on their email.
     *
     * @param email the email of the guest to check in.
     * @return a {@code ResponseEntity} containing a success or error message.
     */
    @PostMapping("/check-in")
    public ResponseEntity<String> checkInGuest(@RequestParam String email) {
        return reservationService.checkInGuestByEmail(email);
    }

    /**
     * Checks out a guest based on their email.
     *
     * @param email the email of the guest to check out.
     * @return a {@code ResponseEntity} containing a success or error message.
     */
    @PostMapping("/check-out")
    public ResponseEntity<String> checkOutGuest(@RequestParam String email) {
        return reservationService.checkOutGuestByEmail(email);
    }
}
