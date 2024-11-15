//package com.example.seaSideEscape.service;
//
//import com.example.seaSideEscape.model.EventBooking;
//import com.example.seaSideEscape.model.Payment;
//import com.example.seaSideEscape.model.PaymentProcess;
//import com.example.seaSideEscape.repository.EventBookingRepository;
//import com.example.seaSideEscape.repository.PaymentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Service
//public class EventPaymentProcessor implements PaymentProcess {
//
//    private final PaymentRepository paymentRepository;
//    private final EventBookingRepository eventBookingRepository;
//
//    @Autowired
//    public EventPaymentProcessor(PaymentRepository paymentRepository, EventBookingRepository eventBookingRepository) {
//        this.paymentRepository = paymentRepository;
//        this.eventBookingRepository = eventBookingRepository;
//    }
//
//    @Override
//    public Payment processPayment(Long eventBookingId, String paymentMethod, String billingAddress, BigDecimal amount,
//                                  String cardNumber, String expirationDate, String csv) {
//        EventBooking eventBooking = eventBookingRepository.findById(eventBookingId)
//                .orElseThrow(() -> new IllegalArgumentException("Event booking not found"));
//
//        // Process payment
//        Payment payment = new Payment();
//        payment.setEventBooking(eventBooking);
//        payment.setPaymentMethod(paymentMethod);
//        payment.setBillingAddress(billingAddress);
//        payment.setAmount(amount);
//        payment.setPaymentDate(LocalDateTime.now());
//        payment.setSuccess(true);
//
//        // Set card details
//        payment.setCardNumber(cardNumber);
//        payment.setExpirationDate(expirationDate);
//        payment.setCSV(csv);
//
//        // Save payment
//        paymentRepository.save(payment);
//        return payment;
//    }
//}
