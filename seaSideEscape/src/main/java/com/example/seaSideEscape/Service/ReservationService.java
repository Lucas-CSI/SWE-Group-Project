package com.example.seaSideEscape.Service;

import com.example.seaSideEscape.model.Guest;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;

import java.time.LocalDate;
import java.util.Set;

public class ReservationService {
    private Set<Reservation> reservations;

    ReservationService(Set<Room> rooms, Set<Reservation> resv) {
        this.reservations = resv;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Reservation getReservation(Guest guest) {
        for (Reservation resv : reservations) {
            if (resv.getGuest().equals(guest)) {
                return resv;
            }
        }
        return null;
    }

    public void setReservaitons(Set<Reservation> resv) {
        this.reservations = resv;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public Set<Room> getAvailableRooms(Set<Room> roomsToSearch, LocalDate startDate, LocalDate endDate) {
        for (Reservation resv : reservations) {
            if (resv.getStartDate().isBefore(endDate) && resv.getEndDate().isAfter(startDate)) {
                roomsToSearch.remove(resv.getRoom());
            }
        }
        return roomsToSearch;
    }

    public Set<Room> filterRooms(Set<Room> roomsToFilter, String theme,
                                 String qualityLevel, String bedType, boolean isSmokingAllowed, double maxRate) {
        for (Room room : roomsToFilter) {
            if (theme != null && !room.getTheme().equals(theme)) {
                roomsToFilter.remove(room);
            }
            if (qualityLevel != null && !room.getQualityLevel().equals(qualityLevel)) {
                roomsToFilter.remove(room);
            }
            if (bedType != null && !room.getBedType().equals(bedType)) {
                roomsToFilter.remove(room);
            }
            if (isSmokingAllowed != room.isSmokingAllowed()) {
                roomsToFilter.remove(room);
            }
            if (maxRate != 0 && room.getMaxRate() > maxRate) {
                roomsToFilter.remove(room);
            }
        }
        return roomsToFilter;
    }

}
