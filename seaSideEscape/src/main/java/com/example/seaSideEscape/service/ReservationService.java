package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Room room = reservation.getRoom();
        if(!roomService.roomExists(reservation.getId())){
            throw new Exception("Room does not exist");
        }
        else if(roomService.isRoomBooked(room.getId())) {
            throw new Exception("Error: Room already reserved");
        }

        Reservation newReservation = reservationRepository.save(reservation);
        billingService.generateBill(newReservation.getId());

        return newReservation;
    }
}
