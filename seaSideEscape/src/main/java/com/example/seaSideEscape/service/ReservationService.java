// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService

package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Guest;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BillingService billingService;
    private final RoomService roomService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
    }

    public void removeReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public Reservation getReservation(Guest user) {
        Optional<Reservation> reservation = reservationRepository.findByGuest(user);
        return reservation.orElse(null);
    }

    public Reservation bookRoom(Reservation reservation) throws Exception {
        ReservationValidator reservationValidator = new ReservationValidator(reservation);
        Room room = reservation.getRoom();
        if(reservationValidator.isValid()) {
            List<Room> rooms = roomService.getRoomsBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(
                    room.isSmokingAllowed(),
                    room.getQualityLevel(),
                    room.getBedType(),
                    room.isOceanView(),
                    room.getTheme()
            );
            List<Reservation> reservations = reservationRepository.findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(
                    room.isSmokingAllowed(),
                    room.getQualityLevel(),
                    room.getBedType(),
                    room.isOceanView(),
                    room.getTheme(),
                    reservation.getStartDate(),
                    reservation.getEndDate()
            );


            if (rooms.size() - reservations.size() > 0) {
                reservation.setRoom(rooms.getFirst());
                Reservation newReservation = reservationRepository.save(reservation);
               // billingService.generateBill(newReservation.getId());
                return newReservation;
            } else {
                throw new Exception("No room available.");
            }
        }else{
            throw new Exception("Invalid reservation.");
        }
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        //function to get all available rooms
        List<Room> rooms = roomService.getAllRooms();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            if (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate)) {
                rooms.remove(reservation.getRoom());
            }
        }
        return rooms;
    }

    public List<Room> filterRooms(List<Room> roomsToFilter, String theme,
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
