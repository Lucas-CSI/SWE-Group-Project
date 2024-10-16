package com.example.seaSideEscape.Service;

import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.PaymentRepository;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private  ReservationRepository reservationRepository;

    public Payment processPayment(Long reservationId, String paymentMethod, String billingAddress, BigDecimal amount){

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if(reservation.getTotalAmount().compareTo(amount) != 0){
            throw new IllegalArgumentException("Amount does not match the total amount due.");
        }
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentMethod(paymentMethod);
        payment.setBillingAddress(billingAddress);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setSuccess(true); //Assume for now

        paymentRepository.save(payment);

        reservation.setPaid(true);
        reservationRepository.save(reservation);
        return payment;
    }
}
