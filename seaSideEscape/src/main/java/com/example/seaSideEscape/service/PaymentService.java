package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private  final ReservationRepository reservationRepository;
    private final EventBookingRepository eventBookingRepository;


    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository, EventBookingRepository eventBookingRepository){
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.eventBookingRepository = eventBookingRepository;
    }

    public Payment processRoomPayment(Long reservationId, String paymentMethod, String billingAddress){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        BigDecimal amount = reservation.getRoomRate();
        return createAndSavePayment(reservation, paymentMethod, billingAddress, amount);
    }

//    public Payment processEventPayment(Long eventBookingId, String paymentMethod, String billingAddress){
//        EventBooking eventBooking = eventBookingRepository.findById(eventBookingId)
//                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
//
//        BigDecimal amount = calculateEventAmount(eventBooking);  // Custom calculation for event
//        return createAndSavePayment(eventBooking.getReservation(), paymentMethod, billingAddress, amount);
//    }




    private Payment createAndSavePayment(Reservation reservation, String paymentMethod, String billingAddress, BigDecimal amount){
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentMethod(paymentMethod);
        payment.setBillingAddress(billingAddress);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setSuccess(true);

        paymentRepository.save(payment);
        reservation.setPaid(true);
        reservationRepository.save(reservation);
        return payment;
    }
}