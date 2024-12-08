// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService
// TODO: Add room cache? so less queries have to be made
// TODO: Add expiration date (5-15min range) to unbooked reservations and a SINGULAR thread that removes expired reservations
package com.example.seaSideEscape.service;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.repository.AccountRepository;
import com.example.seaSideEscape.validator.ReservationValidator;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
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
    private final BookingService bookingService;
    private final AccountRepository accountRepository;
    private SerializeModule<Room> roomSerializeModule = new SerializeModule<Room>();
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService, AccountService accountService, BookingService bookingService, AccountRepository accountRepository) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
        this.bookingService = bookingService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseEntity<String> bookReservation(String username) throws Exception {
        Optional<Account> optionalAccount = accountService.findAccountByUsername(username);
        Account account;
        Reservation reservation;

        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            if (account.getUnbookedReservation() != null) {
                reservation = account.getUnbookedReservation();
                reservation.setBooked(true);
                reservationRepository.save(reservation);
                account.addReservation(reservation);
                account.setUnbookedReservation(null);
                accountService.saveAccount(account);
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

    @Transactional
    public ResponseEntity<String> createReservation(LocalDate checkInDate, LocalDate checkOutDate, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Reservation reservation;
        Account accountObject;
        ReservationValidator reservationValidator;

        if(account.isPresent()) {
            accountObject = account.get();
            if(accountObject.getUnbookedReservation() == null) {
                reservation = new Reservation();
                reservation.setCheckInDate(checkInDate);
                reservation.setCheckOutDate(checkOutDate);
                reservation.setBooked(false);
                reservation.setGuest(accountObject);
                reservationValidator = new ReservationValidator(reservation);
                if(reservationValidator.isValid()) {
                    accountObject.setUnbookedReservation(reservation);
                    reservationRepository.save(reservation);
                    accountRepository.save(accountObject);
                }else{
                    System.out.println(reservationValidator.getInvalidItems());
                    return new ResponseEntity<>(reservationValidator.getInvalidItems().values().stream().findFirst().get(), HttpStatus.CONFLICT);
                }
            }
        }else{
            return new ResponseEntity<>("Account not found", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok().body("Reservation created");
    }

    @Transactional
    public ResponseEntity<String> addRoom(Room room, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);
        Account accountObject;
        Reservation accountsReservation;

        //logger.debug("LOGGER STARTING SDJFWSDJGERWJGW");
        //logger.debug(roomSerializeModule.objectToJSON(room));

        if(account.isPresent()) {
            accountObject = account.get();
            accountsReservation = accountObject.getUnbookedReservation();
            room = roomService.findSpecificAvailableRoom(room, accountsReservation.getCheckInDate(), accountsReservation.getCheckOutDate());
            if (room != null) {
                Booking booking = new Booking(accountsReservation, room);
                bookingService.save(booking);
                accountsReservation.addBooking(booking);
                reservationRepository.save(accountsReservation);
                accountRepository.save(accountObject);
            } else {
                return ResponseEntity.badRequest().body("No room available.");
            }
        }else{
            return ResponseEntity.badRequest().body("You must be logged in.");
        }
        return ResponseEntity.ok().body("Room added");
    }
}



/*
if (!rooms.isEmpty()) {
                room = rooms.getFirst();
                reservation.addRoom(room);
                accountObject.addReservation(reservation);
                reservationRepository.save(reservation);
                //billingService.generateBill(reservation.getId());
reservations = accountObject.getReservations();
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