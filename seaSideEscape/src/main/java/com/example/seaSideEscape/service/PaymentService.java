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

    private BigDecimal calculateTotalAmount(List<Charge> charges, BigDecimal roomRate) {
        BigDecimal total = roomRate;
        for (Charge charge : charges) {
            total = total.add(charge.getAmount());
        }
        return total;
    }
}
