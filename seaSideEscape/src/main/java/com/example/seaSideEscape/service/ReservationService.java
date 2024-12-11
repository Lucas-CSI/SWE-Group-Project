// TODO: Fix GenerateBill in BillingService and re-enable in ReservationService
// TODO: Add room cache? so less queries have to be made
// TODO: Add expiration date (5-15min range) to unbooked reservations and a SINGULAR thread that removes expired reservations
package com.example.seaSideEscape.service;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.repository.AccountRepository;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.validator.ReservationValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
    private final PaymentRepository paymentRepository;
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService, RoomService roomService, AccountService accountService, BookingService bookingService, AccountRepository accountRepository,
                              PaymentRepository paymentRepository) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
        this.bookingService = bookingService;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
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

    public Reservation getUnpaidReservation(Account account) {

        return reservationRepository.findByAccountAndPaidFalse(account)
                .orElseThrow(() -> new IllegalArgumentException("No unpaid reservation found for the account"));
    }

    @Transactional
    public void deleteBookingsFromReservation(Reservation reservation) {
        if (reservation.getBookings() != null && !reservation.getBookings().isEmpty()) {
            for (Booking booking : new ArrayList<>(reservation.getBookings())) {

                booking.setRoom(null);
                reservation.getBookings().remove(booking);
                bookingService.delete(booking);
            }
        }
    }

    @Transactional
    public void clearUnbookedReservationReference(Reservation reservation) {
        Account guest = reservation.getGuest();
        if (guest != null && guest.getUnbookedReservation() != null
                && guest.getUnbookedReservation().getId().equals(reservation.getId())) {
            logger.info("Clearing unbooked reservation for account ID: {}", guest.getId());
            guest.setUnbookedReservation(null);
            accountRepository.save(guest);
            logger.info("Unbooked reservation cleared successfully.");
        }
    }

    @Transactional
    public void deleteReservationWithCleanup(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();

            logger.info("Preparing to delete reservation ID: {}", reservationId);

            Account guest = reservation.getGuest();
            if (guest != null && guest.getUnbookedReservation() != null
                    && guest.getUnbookedReservation().getId().equals(reservationId)) {
                logger.info("Clearing unbooked reservation for account ID: {}", guest.getId());
                guest.setUnbookedReservation(null);
                accountRepository.save(guest);
            }

            deleteBookingsFromReservation(reservation);


            if (guest != null && guest.getReservations().contains(reservation)) {
                guest.getReservations().remove(reservation);
                accountRepository.save(guest);
            }

            paymentRepository.deleteAllByReservationId(reservationId);


            reservationRepository.delete(reservation);
            reservationRepository.flush();
            logger.info("Deleted reservation ID: {}", reservationId);
        } else {
            logger.warn("Reservation not found for ID: {}", reservationId);
        }
    }

    @Transactional
    public List<Reservation> getReservationsByEmail(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);

        if (accountOptional.isEmpty()) {
            logger.error("No account found with email: {}", email);
            throw new IllegalArgumentException("No account found with the given email.");
        }

        Account account = accountOptional.get();
        return account.getReservations();
    }

    @Transactional
    public ResponseEntity<String> checkInGuestByEmail(String email) {
        List<Reservation> reservations = getReservationsByEmail(email);

        if (reservations.isEmpty()) {
            logger.warn("No reservations found for email: {}", email);
            return ResponseEntity.badRequest().body("No reservations found for this email.");
        }

        Reservation reservation = reservations.get(0);

        if (reservation.isBooked()) {
            logger.warn("Guest is already checked in for reservation ID: {}", reservation.getId());
            return ResponseEntity.badRequest().body("Guest is already checked in.");
        }

        reservation.setBooked(true);
        reservationRepository.save(reservation);

        logger.info("Guest checked in successfully for reservation ID: {}", reservation.getId());
        return ResponseEntity.ok("Guest checked in successfully.");
    }

    @Transactional
    public ResponseEntity<String> checkOutGuestByEmail(String email) {
        List<Reservation> reservations = getReservationsByEmail(email);

        if (reservations.isEmpty()) {
            logger.warn("No reservations found for email: {}", email);
            return ResponseEntity.badRequest().body("No reservations found for this email.");
        }

        List<Reservation> bookedReservations = reservations.stream()
                .filter(Reservation::isBooked)
                .toList();

        if (bookedReservations.isEmpty()) {
            logger.warn("No booked reservations found for email: {}", email);
            return ResponseEntity.badRequest().body("Guest is not checked in.");
        }

        for (Reservation reservation : bookedReservations) {
            logger.info("Processing check-out for reservation ID: {}", reservation.getId());

            clearUnbookedReservationReference(reservation);
            deleteBookingsFromReservation(reservation);
            deleteReservationWithCleanup(reservation.getId());
        }

        logger.info("All reservations checked out and deleted successfully for email: {}", email);
        return ResponseEntity.ok("Guest checked out and reservation deleted successfully.");
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