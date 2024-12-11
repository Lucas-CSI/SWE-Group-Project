package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserPortalController {
    private final ReservationService reservationService;
    private final SerializeModule<Reservation> serializeModule = new SerializeModule<>();

    @Autowired
    public UserPortalController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<String> getReservations(@CookieValue("username") String username) throws JsonProcessingException {
        return ResponseEntity.ok(serializeModule.listToJSON(reservationService.getReservationsByUsername(username)));
    }
}
