package com.example.seaSideEscape.service;

import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.model.*;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing payments in the SeaSide Escape application.
 * Provides functionality to process payments for room reservations and event bookings.
 */
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final EventBookingRepository eventBookingRepository;
    private final VenueRepository venueRepository;

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
                          EventBookingRepository eventBookingRepository, VenueRepository venueRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.eventBookingRepository = eventBookingRepository;
        this.venueRepository = venueRepository;
    }

    /**
     * Processes a payment for a room reservation.
     *
     * @param reservationId the ID of the reservation to pay for
     * @param paymentMethod the payment method used (e.g., credit card)
     * @param billingAddress the billing address associated with the payment
     * @param amount the amount to be charged
     * @param cardNumber the credit card number
     * @param expirationDate the expiration date of the credit card
     * @param cvv the CVV security code of the credit card
     * @return the {@code Payment} object representing the processed payment
     * @throws IllegalArgumentException if the reservation is not found
     */
    public Payment processRoomPayment(Long reservationId, String paymentMethod, String billingAddress, BigDecimal amount,
                                      String cardNumber, String expirationDate, String cvv) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
      
        BigDecimal roomRate = reservation.getRoomRate();
        List<Charge> charges = reservation.getCharges();
        BigDecimal totalAmount = calculateTotalAmount(charges, roomRate);

        Payment payment = createPayment(paymentMethod, billingAddress, totalAmount, cardNumber, expirationDate, cvv);
        payment.setReservation(reservation);

        paymentRepository.save(payment);
        reservation.setPaid(true);
        reservationRepository.save(reservation);

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
    private Payment createPayment(String paymentMethod, String billingAddress, BigDecimal amount,
                                  String cardNumber, String expirationDate, String cvv) {
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setBillingAddress(billingAddress);
        payment.setAmount(amount);
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
