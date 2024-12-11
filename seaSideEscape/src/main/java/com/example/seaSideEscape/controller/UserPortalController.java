package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Booking;
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

@RestController
@RequestMapping("/profile")
public class UserPortalController {
    private final ReservationService reservationService;
    private final SerializeModule<Reservation> reservationSerializeModule = new SerializeModule<>();
    private final SerializeModule<Room> roomSerializeModule = new SerializeModule<>();
    private final RoomService roomService;

    @Autowired
    public UserPortalController(ReservationService reservationService, BookingService bookingService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<String> getReservations(@CookieValue("username") String username) throws JsonProcessingException {
        List<Reservation> reservations = reservationService.getReservationsByUsername(username);

        return ResponseEntity.ok(reservationSerializeModule.listToJSON(reservations));
    }
}
