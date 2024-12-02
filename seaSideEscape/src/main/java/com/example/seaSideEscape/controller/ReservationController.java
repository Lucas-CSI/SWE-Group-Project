package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.ReservationService;
import com.example.seaSideEscape.service.RoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    @Autowired
    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookReservation(@CookieValue("username") String username) throws Exception {
        return reservationService.bookReservation(username);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createReservation(@RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                    @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                    @CookieValue("username") String username) throws Exception {
        return reservationService.createReservation(checkInDate, checkOutDate, username);
    }

    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody Room room, @CookieValue("username") String username) throws Exception {
        return reservationService.addRoom(room, username);
    }

    @PostMapping("/fillDB")
    public void fillDB() throws Exception {
        roomService.setupDB();
    }
}
