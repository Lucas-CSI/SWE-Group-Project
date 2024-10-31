// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService

package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BillingService billingService;
    private final RoomService roomService;
    private final AccountService accountService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService, AccountService accountService) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
    }

    public Reservation bookReservation(Reservation reservation) throws Exception {
        ReservationValidator reservationValidator = new ReservationValidator(reservation);
        if(reservationValidator.isValid()) {
            return reservationRepository.save(reservation);
        }else{
            throw new Exception("Invalid reservation.");
        }
    }

    public Room addRoom(Room room, String username) throws Exception {
        RoomValidator roomValidator = new RoomValidator(room);
        if (!roomValidator.isValid()) {
            throw new Exception("Invalid room details: " + roomValidator.getInvalidItems());
        }

        Optional<Account> account = accountService.findAccountByUsername(username);
        Reservation reservation;

        if(account.isPresent()) {
            reservation = account.get().getReservation();
            Set<Room> roomSet = new HashSet<>(reservation.getRooms());
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
            rooms = rooms.stream()
                    .filter(roomSet::contains)
                    .toList();
            if (rooms.size() - reservations.size() > 0) {
                room = rooms.getFirst();
                reservation.addRoom(room);
                // billingService.generateBill(newReservation.getId());
            } else {
                throw new Exception("No room available.");
            }
        }else{
            throw new Exception("You must be logged in.");
        }
        return room;
    }
}
