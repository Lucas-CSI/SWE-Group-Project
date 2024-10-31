// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService

package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.*;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final BillingService billingService;
    private final RoomService roomService;
//    private final UserService userService;
//    private final AccountService accountService;
//    private final JwtService jwtService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService,
                              RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
//        this.userService = userService;
//        this.accountService = accountService;
//        this.jwtService = jwtService;
    }

    public Reservation bookRoom(Reservation reservation) throws Exception {
        ReservationValidator reservationValidator = new ReservationValidator(reservation);
        Room room = reservation.getRoom();
//        Guest guest;

//        if (token != null) {
//            Account account = jwtService.getAccountFromToken(token)
//                    .orElseThrow(() -> new Exception("Invalid or expired token"));
//
//            if (account instanceof Guest) {
//                guest = (Guest) account; // Downcast to Guest
//            } else {
//                throw new Exception("Only guests can book rooms.");
//            }
//        } else if (sessionKey != null) {
//            guest = (Guest) userService.getOrCreateSessionUser(sessionKey);
//        } else {
//            throw new Exception("User must be logged in or provide a session key.");
//        }

//        reservation.setGuest(guest);

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

//            if (rooms.size() > reservations.size()) {
//                reservation.setRoom(rooms.get(0));
//                Reservation newReservation = reservationRepository.save(reservation);
//
//                logger.info("Reservation successfully created for guest ID: {}", guest.getId());
//                return newReservation;
//            } else {
//                throw new Exception("No available rooms that match the reservation criteria.");
//            }
//        } else {
//            throw new Exception("Invalid reservation details provided.");
//        }

            if (rooms.size() - reservations.size() > 0) {
                reservation.setRoom(rooms.getFirst());
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
