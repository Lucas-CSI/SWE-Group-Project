package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/book")
    public List<Reservation> bookReservation(@CookieValue("username") String username) throws Exception {
        return reservationService.bookReservation(username);
    }

    @PostMapping("/addRoom")
    public Room addRoom(@RequestBody Reservation reservation, @CookieValue("username") String username) throws Exception {
        return reservationService.addRoom(reservation, username);
    }
}
