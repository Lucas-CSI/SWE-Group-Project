package com.example.seaSideEscape.service;

import com.example.seaSideEscape.controller.PaymentController;
import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.model.*;
import com.example.seaSideEscape.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing payments in the SeaSide Escape application.
 * Provides functionality to process payments for room reservations and event bookings.
 */
@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);


    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final EventBookingRepository eventBookingRepository;
    private final VenueRepository venueRepository;
    private final RoomService roomService;
    private final EmailService emailService;
    private final BookingRepository bookingRepository;

    /**
     * Constructs a new {@code PaymentService}.
     *
     * @param paymentRepository the repository for managing payment entities
     * @param reservationRepository the repository for managing reservations
     * @param eventBookingRepository the repository for managing event bookings
     * @param venueRepository the repository for managing venue entities
     */
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository,
                          EventBookingRepository eventBookingRepository, VenueRepository venueRepository, RoomService roomService, EmailService emailService, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.eventBookingRepository = eventBookingRepository;
        this.venueRepository = venueRepository;
        this.roomService = roomService;
        this.emailService = emailService;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Processes a payment for a room reservation.
     *
     * @param paymentMethod the payment method used (e.g., credit card)
     * @param billingAddress the billing address associated with the payment
     * @param cardNumber the credit card number
     * @param expirationDate the expiration date of the credit card
     * @param cvv the CVV security code of the credit card
     * @return the {@code Payment} object representing the processed payment
     * @throws IllegalArgumentException if the reservation is not found
     */
    public Payment processRoomPayment(String paymentMethod, String billingAddress, String cardNumber,
                                      String expirationDate, String cvv, Account account) {

        Reservation reservation = reservationRepository.findByAccountAndPaidFalse(account)
                .orElseThrow(() -> new IllegalArgumentException("No unpaid reservations found for the account"));
        log.info("Found unpaid reservation with ID: {}", reservation.getId());

        List<Room> roomsInCart = roomService.getRoomsInCart(account.getUsername());
        roomService.clearCart(account.getUsername());
        if (roomsInCart == null || roomsInCart.isEmpty()) {
            throw new IllegalArgumentException("No rooms found in the cart for this reservation.");
        }
        log.info("Rooms in cart for username '{}': {}", account.getUsername(),
                roomsInCart.stream().map(Room::getId).toList());
        log.info("Starting processRoomPayment for username: {}", account.getUsername());

        double totalRoomCost = roomService.calculateTotalCartCost(account.getUsername());
        double tax = totalRoomCost * 0.1; // 10% tax rate
        double totalCost = totalRoomCost + tax;

        log.info("Processing payment: Room Cost = {}, Tax = {}, Total Cost = {}", totalRoomCost, tax, totalCost);

        Payment payment = createPayment(paymentMethod, billingAddress, totalCost, cardNumber, expirationDate, cvv);
        payment.setReservation(reservation);
        paymentRepository.save(payment);

        if (roomsInCart == null || roomsInCart.isEmpty()) {
            throw new IllegalArgumentException("No rooms found in the cart for this reservation.");
        }



        Set<Long> roomIds = new HashSet<>();
        for (Room room : roomsInCart) {
            if (!roomIds.add(room.getId())) {
                throw new IllegalStateException("Duplicate room detected in cart for username: " + account.getUsername());
            }

            Booking booking = new Booking();
            booking.setReservation(reservation);
            booking.setRoom(room);

            bookingRepository.save(booking);
        }


        log.info("Saved {} bookings for reservation ID: {}", roomsInCart.size(), reservation.getId());
        log.info("Finished processRoomPayment for username: {}", account.getUsername());

        reservation.setPaid(true);
        reservationRepository.save(reservation);

        LocalDateTime checkInDateTime = reservation.getCheckInDate().atStartOfDay();
        LocalDateTime checkOutDateTime = reservation.getCheckOutDate().atStartOfDay();

        emailService.sendReservationConfirmation(
                reservation.getGuest().getEmail(),
                reservation.getId(),
                roomsInCart,
                checkInDateTime,
                checkOutDateTime
        );

        log.info("Payment processed successfully for account: {}", account.getUsername());
        return payment;
    }


    /**
     * Processes a payment for an event booking.
     *
     * @param bookingPaymentRequest the details of the booking and payment request
     * @return the {@code Payment} object representing the processed payment
     * @throws IllegalArgumentException if the event booking is not found
     */
    public Payment bookAndPayEvent(BookingPaymentRequest bookingPaymentRequest) {
        EventBooking eventBooking = eventBookingRepository.findById(bookingPaymentRequest.getEventBookingId())
                .orElseThrow(() -> new IllegalArgumentException("EventBooking not found"));

        Payment payment = createPayment(
                bookingPaymentRequest.getPaymentMethod(),
                bookingPaymentRequest.getBillingAddress(),
                bookingPaymentRequest.getAmount(),
                bookingPaymentRequest.getCardNumber(),
                bookingPaymentRequest.getExpirationDate(),
                bookingPaymentRequest.getCvv()
        );

        payment.setEventBooking(eventBooking);

        paymentRepository.save(payment);

        Venue venue = eventBooking.getVenue();
        venue.setPaid(true);
        venueRepository.save(venue);

        eventBooking.setPaid(true);
        eventBookingRepository.save(eventBooking);

        return payment;
    }

    /**
     * Creates a payment object with the provided details.
     *
     * @param paymentMethod the payment method used
     * @param billingAddress the billing address associated with the payment
     * @param amount the amount to be charged
     * @param cardNumber the credit card number
     * @param expirationDate the expiration date of the credit card
     * @param cvv the CVV security code of the credit card
     * @return a new {@code Payment} object populated with the provided details
     */
    private Payment createPayment(String paymentMethod, String billingAddress, double amount,
                                  String cardNumber, String expirationDate, String cvv) {
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setBillingAddress(billingAddress);
        payment.setAmount(BigDecimal.valueOf(amount));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setSuccess(true);
        payment.setCardNumber(cardNumber);
        payment.setExpirationDate(expirationDate);
        payment.setCSV(cvv);
        return payment;
    }

    /**
     * Calculates the total payment amount for a venue, including its base rate and any additional charges.
     *
     * @param venue the venue for which to calculate the total amount
     * @return the total payment amount for the venue
     */
    private BigDecimal calculateVenueTotalAmount(Venue venue) {
        BigDecimal baseRate = venue.getBaseRate() != null ? venue.getBaseRate() : BigDecimal.ZERO;
        BigDecimal additionalCharges = venue.getAdditionalCharges() != null ? venue.getAdditionalCharges() : BigDecimal.ZERO;
        return baseRate.add(additionalCharges);
    }

    /**
     * Calculates the total payment amount for a reservation, including room rate and additional charges.
     *
     * @param charges the list of additional charges associated with the reservation
     * @param roomRate the base rate for the room
     * @return the total payment amount for the reservation
     */
    private BigDecimal calculateTotalAmount(List<Charge> charges, BigDecimal roomRate) {
        BigDecimal total = roomRate;
        for (Charge charge : charges) {
            total = total.add(charge.getAmount());
        }
        return total;
    }
}
