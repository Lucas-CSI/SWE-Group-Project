package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Guest;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/book")
    public Reservation bookRoom(@RequestBody Reservation reservation) throws Exception {
        return reservationService.bookRoom(reservation);
    }

    public Reservation accessReservation(Guest user) {
        return reservationService.getReservation(user);
    }

    //TODO - Should ensure all values are filled
    public Reservation editReservation(LocalDate startDate, LocalDate endDate, Room room, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setRoom(room);
        reservation.setGuest(guest);
        return reservation;
    }

    public Reservation confirmChanges(Reservation oldReservation, Reservation newReservation, Boolean confirm) throws Exception {
        if (confirm) {
            reservationService.removeReservation(oldReservation);
            reservationService.bookRoom(newReservation);
            return newReservation;
        }
        return oldReservation;
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return reservationService.getAvailableRooms(startDate, endDate);
    }

    public List<Room> filterRooms(List<Room> roomsToFilter, String theme,
                                  String qualityLevel, String bedType, boolean isSmokingAllowed, double maxRate) {
        return reservationService.filterRooms(roomsToFilter, theme, qualityLevel, bedType, isSmokingAllowed, maxRate);
    }
}
