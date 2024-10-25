package com.example.seaSideEscape;

import com.example.seaSideEscape.Service.ReservationService;
import com.example.seaSideEscape.model.Guest;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;

import java.time.LocalDate;
import java.util.Set;

public class ReservationController {
    private ReservationService reservationService;

    public Reservation accessReservation(Guest user) {
        return reservationService.getReservation(user);
    }

    //FIX ME - Should ensure all values are filled
    public Reservation editReservation(LocalDate startDate, LocalDate endDate, Room room, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setRoom(room);
        reservation.setGuest(guest);
        return reservation;
    }

    public Reservation confirmChanges(Reservation oldReservation, Reservation newReservation, Boolean confirm) {
        if (confirm) {
            reservationService.removeReservation(oldReservation);
            reservationService.addReservation(newReservation);
            return newReservation;
        }
        return oldReservation;
    }

    public Set<Room> getAvailableRooms(Set<Room> roomsToSearch, LocalDate startDate, LocalDate endDate) {
        return reservationService.getAvailableRooms(roomsToSearch, startDate, endDate);
    }

    public Set<Room> filterRooms(Set<Room> roomsToFilter, String theme,
                                 String qualityLevel, String bedType, boolean isSmokingAllowed, double maxRate) {
        return reservationService.filterRooms(roomsToFilter, theme, qualityLevel, bedType, isSmokingAllowed, maxRate);
    }
}
