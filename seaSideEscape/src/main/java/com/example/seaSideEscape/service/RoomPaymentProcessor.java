//package com.example.seaSideEscape.service;
//
//import com.example.seaSideEscape.model.Payment;
//import com.example.seaSideEscape.model.PaymentProcess;
//import com.example.seaSideEscape.model.Reservation;
//import com.example.seaSideEscape.repository.PaymentRepository;
//import com.example.seaSideEscape.repository.ReservationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Service
//public class RoomPaymentProcessor implements PaymentProcess {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Override
//    public Payment processPayment(Long reservationId, String paymentMethod, String billingAddress, BigDecimal amount, String cardNumber, String expiryDate, String csv) {
//        Reservation reservation = reservationRepository.findById(reservationId)
//                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
//
//        Payment payment = new Payment();
//        payment.setReservation(reservation);
//        payment.setPaymentMethod(paymentMethod);
//        payment.setBillingAddress(billingAddress);
//        payment.setAmount(amount);
//        payment.setPaymentDate(LocalDateTime.now());
//        payment.setSuccess(true);
//
//        paymentRepository.save(payment);
//
//        payment.setCardNumber(cardNumber);
//        payment.setExpirationDate(expiryDate);
//        payment.setCSV(csv);
//
//        reservation.setPaid(true);
//        reservationRepository.save(reservation);
//        return payment;
//    }
//}
