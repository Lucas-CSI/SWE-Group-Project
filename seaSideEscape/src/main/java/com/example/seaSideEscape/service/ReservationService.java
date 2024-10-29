// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService

package com.example.seaSideEscape.service;

import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
