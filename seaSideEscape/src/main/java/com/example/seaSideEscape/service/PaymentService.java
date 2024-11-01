package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.model.PaymentProcess;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService implements PaymentProcess {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final EventBookingRepository eventBookingRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository, EventBookingRepository eventBookingRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public Payment processPayment(Long id, String paymentMethod, String billingAddress, BigDecimal amount, boolean isRoomPayment) {
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setBillingAddress(billingAddress);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setSuccess(true);

        // Determine payment amount and associate with reservation or event booking
        if (isRoomPayment) {
            Reservation reservation = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
            payment.setReservation(reservation);

            amount = calculateRoomTotalAmount(reservation);
            reservation.setPaid(true);
            reservationRepository.save(reservation);
        } else {
            EventBooking eventBooking = eventBookingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Event booking not found"));
            payment.setEventBooking(eventBooking);

            amount = calculateVenueTotalAmount(eventBooking.getVenue());
        }

        payment.setAmount(amount);  // Set calculated amount
        paymentRepository.save(payment);

        return payment;
    }

    // Calculate total room amount based on room rate and additional charges
    private BigDecimal calculateRoomTotalAmount(Reservation reservation) {
        BigDecimal roomRate = reservation.getRoomRate() != null ? reservation.getRoomRate() : BigDecimal.ZERO;
        BigDecimal additionalCharges = reservation.getCharges().stream()
                .map(charge -> charge.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return roomRate.add(additionalCharges);
    }

    // Calculate total venue amount based on venue base rate and additional charges
    private BigDecimal calculateVenueTotalAmount(Venue venue) {
        BigDecimal baseRate = venue.getBaseRate() != null ? venue.getBaseRate() : BigDecimal.ZERO;
        BigDecimal additionalCharges = venue.getAdditionalCharges() != null ? venue.getAdditionalCharges() : BigDecimal.ZERO;
        return baseRate.add(additionalCharges);
    }
}
