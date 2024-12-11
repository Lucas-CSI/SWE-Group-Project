package com.example.seaSideEscape.service;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.AccountRepository;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.validator.ReservationValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing reservations.
 * Provides functionalities such as creating, booking, and deleting reservations,
 * as well as managing bookings and room assignments within reservations.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BillingService billingService;
    private final RoomService roomService;
    private final AccountService accountService;
    private final BookingService bookingService;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final SerializeModule<Room> roomSerializeModule = new SerializeModule<>();
    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    /**
     * Constructs a new ReservationService with the required dependencies.
     *
     * @param reservationRepository the repository for reservations
     * @param billingService        the service for billing operations
     * @param roomService           the service for room management
     * @param accountService        the service for account management
     * @param bookingService        the service for booking management
     * @param accountRepository     the repository for accounts
     * @param paymentRepository     the repository for payments
     */
    public ReservationService(ReservationRepository reservationRepository, BillingService billingService,
                              RoomService roomService, AccountService accountService, BookingService bookingService,
                              AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.reservationRepository = reservationRepository;
        this.billingService = billingService;
        this.roomService = roomService;
        this.accountService = accountService;
        this.bookingService = bookingService;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Books an unbooked reservation for a given username.
     *
     * @param username the username of the account making the booking
     * @return a ResponseEntity indicating the success or failure of the operation
     * @throws Exception if an error occurs during booking
     */
    @Transactional
    public ResponseEntity<String> bookReservation(String username) throws Exception {
        Optional<Account> optionalAccount = accountService.findAccountByUsername(username);
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();
        Reservation reservation = account.getUnbookedReservation();

        if (reservation == null) {
            return ResponseEntity.badRequest().body("No reservation to book.");
        }

        reservation.setBooked(true);
        reservationRepository.save(reservation);
        account.addReservation(reservation);
        account.setUnbookedReservation(null);
        accountService.saveAccount(account);

        return ResponseEntity.ok("Reservation booked");
    }

    /**
     * Creates a new reservation for a specified check-in and check-out date.
     *
     * @param checkInDate  the check-in date
     * @param checkOutDate the check-out date
     * @param username     the username of the account creating the reservation
     * @return a ResponseEntity indicating the success or failure of the operation
     * @throws Exception if an error occurs during reservation creation
     */
    @Transactional
    public ResponseEntity<String> createReservation(LocalDate checkInDate, LocalDate checkOutDate, String username) throws Exception {
        Optional<Account> accountOptional = accountService.findAccountByUsername(username);

        if (accountOptional.isEmpty()) {
            return new ResponseEntity<>("Account not found", HttpStatus.CONFLICT);
        }

        Account account = accountOptional.get();
        if (account.getUnbookedReservation() != null) {
            return ResponseEntity.ok("Reservation already exists.");
        }

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setBooked(false);
        reservation.setGuest(account);

        ReservationValidator validator = new ReservationValidator(reservation);
        if (!validator.isValid()) {
            return new ResponseEntity<>(validator.getInvalidItems().values().stream().findFirst().orElse("Invalid reservation data"), HttpStatus.CONFLICT);
        }

        account.setUnbookedReservation(reservation);
        reservationRepository.save(reservation);
        accountRepository.save(account);

        return ResponseEntity.ok("Reservation created");
    }

    /**
     * Deletes all bookings associated with a given reservation.
     *
     * @param reservation the reservation whose bookings are to be deleted
     */
    @Transactional
    public void deleteBookingsFromReservation(Reservation reservation) {
        if (reservation.getBookings() != null && !reservation.getBookings().isEmpty()) {
            for (Booking booking : new ArrayList<>(reservation.getBookings())) {
                logger.info("Deleting booking ID: {}", booking.getId());
                booking.setRoom(null);
                reservation.getBookings().remove(booking);
                bookingService.delete(booking);
            }
        }
    }

    /**
     * Clears the reference to an unbooked reservation in the associated account.
     *
     * @param reservation the reservation whose reference is to be cleared
     */
    @Transactional
    public void clearUnbookedReservationReference(Reservation reservation) {
        Account guest = reservation.getGuest();
        if (guest != null && guest.getUnbookedReservation() != null
                && guest.getUnbookedReservation().getId().equals(reservation.getId())) {
            guest.setUnbookedReservation(null);
            accountRepository.save(guest);
        }
    }

    /**
     * Deletes a reservation and performs cleanup tasks such as removing bookings and clearing references.
     *
     * @param reservationId the ID of the reservation to delete
     */
    @Transactional
    public void deleteReservationWithCleanup(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            logger.warn("Reservation not found for ID: {}", reservationId);
            return;
        }

        Reservation reservation = reservationOptional.get();
        clearUnbookedReservationReference(reservation);
        deleteBookingsFromReservation(reservation);
        reservationRepository.delete(reservation);
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

        reservation.setCheckedIn(true);
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



    /**
     * Retrieves reservations associated with a specific email.
     *
     * @param email the email of the account
     * @return a list of reservations associated with the email
     */
    @Transactional
    public List<Reservation> getReservationsByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(Account::getReservations)
                .orElseThrow(() -> new IllegalArgumentException("No account found with the given email."));
    }

    /**
     * Adds a room to the unbooked reservation of a user by finding an available room matching the specified criteria.
     *
     * @param room     the room details to match
     * @param username the username of the account
     * @return a ResponseEntity indicating the success or failure of the operation
     * @throws Exception if the room cannot be added
     */
    @Transactional
    public ResponseEntity<String> addRoom(Room room, String username) throws Exception {
        Optional<Account> account = accountService.findAccountByUsername(username);

        if (account.isEmpty()) {
            return ResponseEntity.badRequest().body("You must be logged in.");
        }

        Account accountObject = account.get();
        Reservation accountsReservation = accountObject.getUnbookedReservation();

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

        return ResponseEntity.ok().body("Room added");
    }

    /**
     * Retrieves reservations associated with a specific username.
     *
     * @param username the username of the account
     * @return a list of reservations associated with the username
     */
    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findAllReservationsByUser(username);
    }

    /**
     * Retrieves the first unpaid reservation for a given account.
     *
     * @param account the account whose unpaid reservation is to be retrieved
     * @return the unpaid reservation
     * @throws IllegalArgumentException if no unpaid reservation is found
     */
    public Reservation getUnpaidReservation(Account account) {
        return reservationRepository.findByAccountAndPaidFalse(account)
                .orElseThrow(() -> new IllegalArgumentException("No unpaid reservation found for the account"));
    }
}
