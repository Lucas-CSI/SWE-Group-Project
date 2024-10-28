//package com.example.seaSideEscape;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    public void sendPaymentConfirmation(String to, Payment payment) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Payment Confirmation");
//        message.setText("Dear Customer, your payment of " + payment.getAmount() +
//                " for reservation ID: " + payment.getReservation().getId() + " has been processed successfully.");
//        emailSender.send(message);
//    }
//}
