package com.example.seaSideEscape.service;

import com.example.seaSideEscape.inputValidation.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Reservation bookRoom(Reservation reservation, boolean oceanView, Room.Themes theme) throws Exception {
        ReservationValidator reservationValidator = new ReservationValidator(reservation);
        if(reservationValidator.isValid()) {
            Optional<Room> availableRoom = roomService.getAvailableRoomWithViewAndTheme(oceanView, theme);

            if (availableRoom.isPresent()) {
                Reservation newReservation = reservationRepository.save(reservation);
                billingService.generateBill(newReservation.getId());
                return newReservation;
            } else {
                throw new Exception("No room available.");
            }
        }else{
            throw new Exception("Invalid reservation.");
        }
    }
}
