package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BillingService billingService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
    }

    public Reservation bookRoom(Reservation reservation) throws Exception {
        if(reservationRepository.findByRoom(reservation.getRoom()).isPresent()) {
            throw new Exception("Error: Room already reserved");
        }
        Reservation newReservation = reservationRepository.save(reservation);
        billingService.generateBill(newReservation.getId());

        return newReservation;
    }
}
