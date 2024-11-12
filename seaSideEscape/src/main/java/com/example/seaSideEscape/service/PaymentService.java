package com.example.seaSideEscape.service;

import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final EventBookingRepository eventBookingRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository,
                          EventBookingRepository eventBookingRepository, VenueRepository venueRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.eventBookingRepository = eventBookingRepository;
        this.venueRepository = venueRepository;
    }

    public Payment processRoomPayment(Long reservationId, String paymentMethod, String billingAddress, BigDecimal amount,
                                      String cardNumber, String expirationDate, String cvv) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        amount = amount != null ? amount : reservation.getRoomRate(); // Use room rate if amount is null
        Payment payment = createPayment(paymentMethod, billingAddress, amount, cardNumber, expirationDate, cvv);
        payment.setReservation(reservation);

        paymentRepository.save(payment);
        reservation.setPaid(true);
        reservationRepository.save(reservation);

        return payment;
    }

    public Payment bookAndPayEvent(BookingPaymentRequest bookingPaymentRequest) {
        // Retrieve the existing event booking using eventBookingId
        EventBooking eventBooking = eventBookingRepository.findById(bookingPaymentRequest.getEventBookingId())
                .orElseThrow(() -> new IllegalArgumentException("EventBooking not found"));

        // Create the payment
        Payment payment = createPayment(
                bookingPaymentRequest.getPaymentMethod(),
                bookingPaymentRequest.getBillingAddress(),
                bookingPaymentRequest.getAmount(),
                bookingPaymentRequest.getCardNumber(),
                bookingPaymentRequest.getExpirationDate(),
                bookingPaymentRequest.getCvv()
        );

        // Associate the payment with the event booking
        payment.setEventBooking(eventBooking);

        // Save the payment
        paymentRepository.save(payment);

        // Mark the venue as paid and save the update
        Venue venue = eventBooking.getVenue();
        venue.setPaid(true);
        venueRepository.save(venue);

        eventBooking.setPaid(true);
        eventBookingRepository.save(eventBooking);

        return payment;
    }


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

    private BigDecimal calculateVenueTotalAmount(Venue venue) {
        BigDecimal baseRate = venue.getBaseRate() != null ? venue.getBaseRate() : BigDecimal.ZERO;
        BigDecimal additionalCharges = venue.getAdditionalCharges() != null ? venue.getAdditionalCharges() : BigDecimal.ZERO;
        return baseRate.add(additionalCharges);
    }
}
