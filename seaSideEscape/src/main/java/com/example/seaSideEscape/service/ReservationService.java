// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService
// TODO: Add cache so less queries have to be made
package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.Transient;
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
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService, AccountService accountService) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @Transactional
    public ResponseEntity<String> bookReservation(String username) throws Exception {
        Optional<Account> optionalAccount = accountService.findAccountByUsername(username);
        Account account;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            if (account.getUnbookedReservation() != null) {
                account.addReservation(account.getUnbookedReservation());
                account.setUnbookedReservation(null);
                accountService.saveAccount(account);
                reservationRepository.save(account.getUnbookedReservation());
            } else
                return ResponseEntity.badRequest().body("No reservation to book.");
        }else{
            return ResponseEntity.badRequest().body("Account not found");
        }

        return ResponseEntity.ok().body("Reservation booked");
    }

    /*public Room addRoom(Room room, String username) {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Reservation reservation = account.get().getReservation();
        if(reservation == null) {
            reservation = new Reservation();
            account.get().setReservation(reservation);
        }
        reservation.addRoom(room);
        reservationRepository.save(reservation);
        accountService.saveAccount(account.get());
        return room;
    }*/



    public Room addRoom(Reservation reservation, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject;
        List<Reservation> reservations;
        Room room;

        if(account.isPresent()) {
            accountObject = account.get();
            reservations = accountObject.getReservations();
            reservation.setGuest(accountObject);
            room = reservation.getBookings().getFirst();
            if(reservations == null) {
                reservations = new ArrayList<>(){
                    public boolean add(Reservation mt) {
                        int index = Collections.binarySearch(this, mt, Reservation.Sort.StartDate);
                        if (index < 0) index = ~index;
                        super.add(index, mt);
                        return true;
                    }
                };
                accountObject.setReservations(reservations);
            }

            reservationsInDB.forEach(res -> {
                takenRooms.addAll(res.getRooms());
            });
            logger.debug("STARTING DEBUG......");
            logger.debug("Rooms: ");
            rooms.forEach(rooms2 -> logger.debug(rooms2.getRoomNumber()));
            rooms = rooms.stream()
                    .filter(room2 -> !takenRooms.contains(room2))
                    .toList();
            if (!rooms.isEmpty()) {
                room = rooms.getFirst();
                reservation.addRoom(room);
                accountObject.addReservation(reservation);
                reservationRepository.save(reservation);
                //billingService.generateBill(reservation.getId());
            } else {
                throw new Exception("No room available.");
            }
        }else{
            throw new Exception("You must be logged in.");
        }
        return room;
    }
}

/*
OLD QUERY (In case we want to use it later
            List<Room> rooms = roomService.getRoomsBySmokingAllowedByQualityLevelAndBedTypeAndViewAndTheme(
                    room.isSmokingAllowed(),
                    room.getQualityLevel(),
                    room.getBedType(),
                    room.isOceanView(),
                    room.getTheme()
            );
            List<Reservation> reservationsInDB = reservationRepository.findBySmokingAllowedByQualityLevelAndBedTypeAndViewAndThemeBetweenCheckInDateAndCheckOutDate(
                    room.isSmokingAllowed(),
                    room.getQualityLevel(),
                    room.getBedType(),
                    room.isOceanView(),
                    room.getTheme(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate()
            );
 */