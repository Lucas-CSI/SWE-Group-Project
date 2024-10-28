package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

//    public Reservation bookRoom(Reservation reservation) throws Exception {
//        if(reservationRepository.findByRoom(reservation.getRoom()).isPresent()) {
//            throw new Exception("Error: Room already reserved");
//        }
//        return reservationRepository.save(reservation);
//    }
}
