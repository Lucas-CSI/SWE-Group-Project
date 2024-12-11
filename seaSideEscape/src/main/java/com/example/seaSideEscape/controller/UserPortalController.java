package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.BookingService;
import com.example.seaSideEscape.service.ReservationService;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing user-specific operations in the SeaSide Escape application.
 * Provides endpoints for retrieving user reservations.
 */
@RestController
@RequestMapping("/profile")
public class UserPortalController {

    private final ReservationService reservationService;
    private final SerializeModule<Reservation> reservationSerializeModule = new SerializeModule<>();
    private final SerializeModule<Room> roomSerializeModule = new SerializeModule<>();
    private final RoomService roomService;

    /**
     * Constructs a new {@code UserPortalController}.
     *
     * @param reservationService the service handling reservation-related operations.
     * @param bookingService     the service handling booking-related operations (not used in the current implementation).
     * @param roomService        the service handling room-related operations.
     */
    @Autowired
    public UserPortalController(ReservationService reservationService, BookingService bookingService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    /**
     * Retrieves the list of reservations for the logged-in user.
     *
     * @param username the username of the logged-in user, retrieved from cookies.
     * @return a {@link ResponseEntity} containing a JSON string representation of the user's reservations.
     * @throws JsonProcessingException if an error occurs during JSON serialization.
     */
    @GetMapping("/reservations")
    public ResponseEntity<String> getReservations(@CookieValue("username") String username) throws JsonProcessingException {
        List<Reservation> reservations = reservationService.getReservationsByUsername(username);

        return ResponseEntity.ok(reservationSerializeModule.listToJSON(reservations));
    }
}
