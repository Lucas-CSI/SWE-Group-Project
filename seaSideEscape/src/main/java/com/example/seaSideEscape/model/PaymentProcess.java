package com.example.seaSideEscape.model;

import java.math.BigDecimal;

public interface PaymentProcess {
    Payment processPayment(Long reservationOrBookingId, String paymentMethod, String billingAddress, BigDecimal amount,
                           String cardNumber, String expirationDate, String cvv);
}
