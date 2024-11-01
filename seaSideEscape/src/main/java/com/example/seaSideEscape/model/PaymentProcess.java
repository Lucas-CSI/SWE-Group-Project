package com.example.seaSideEscape.model;


import java.math.BigDecimal;

public interface PaymentProcess {
    Payment processPayment(Reservation reservation, String paymentMethod, String billingAddress, BigDecimal amount);
}
