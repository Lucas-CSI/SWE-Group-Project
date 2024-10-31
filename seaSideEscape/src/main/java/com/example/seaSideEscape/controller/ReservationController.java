package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/book")
    public Reservation bookReservation(@RequestBody Reservation reservation) throws Exception {
        return reservationService.bookReservation(reservation);
    }

    @PostMapping("/addRoom")
    public Room addRoom(@RequestBody Room room, @CookieValue("username") String username) throws Exception {
        return reservationService.addRoom(room, username);
    }
}
