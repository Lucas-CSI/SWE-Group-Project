package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.ReservationService;
import com.example.seaSideEscape.service.RoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;


/**
 * REST controller for handling reservation-related requests in the SeaSide Escape application.
 * Provides endpoints for creating, booking, and managing reservations.
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
     * @param reservationService the service handling reservation-related operations
     * @param roomService the service handling room-related operations
     */
    @Autowired
    public ReservationController(ReservationService reservationService, RoomService roomService, ReservationRepository reservationRepository, AccountService accountService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.reservationRepository = reservationRepository;
        this.accountService = accountService;
    }

    /**
     * Endpoint to book an existing reservation for the logged-in user.
     *
     * @param username the username of the logged-in user (retrieved from a cookie)
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the booking process
     */
    @PostMapping("/book")
    public ResponseEntity<String> bookReservation(@CookieValue("username") String username) throws Exception {
        return reservationService.bookReservation(username);
    }

    /**
     * Endpoint to create a new reservation for the logged-in user.
     *
     * @param checkInDate the start date of the reservation, formatted as ISO 8601
     * @param checkOutDate the end date of the reservation, formatted as ISO 8601
     * @param username the username of the logged-in user (retrieved from a cookie)
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the reservation creation process
     */
    @PostMapping("/new")
    public ResponseEntity<String> createReservation(@RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                    @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                    @CookieValue("username") String username) throws Exception {
        return reservationService.createReservation(checkInDate, checkOutDate, username);
    }

    /**
     * Endpoint to add a room to the user's current reservation.
     *
     * @param room the room to be added, passed as a JSON object in the request body
     * @param username the username of the logged-in user (retrieved from a cookie)
     * @return a {@code ResponseEntity} containing a success or error message
     * @throws Exception if an error occurs during the room addition process
     */
    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody Room room, @CookieValue("username") String username) throws Exception {
        return reservationService.addRoom(room, username);
    }

    /**
     * Endpoint to populate the database with initial room data.
     * Intended for administrative use or during development.
     *
     * @throws Exception if an error occurs during the database setup process
     */
    @PostMapping("/fillDB")
    public void fillDB() throws Exception {
        roomService.setupDB();
    }

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
}
