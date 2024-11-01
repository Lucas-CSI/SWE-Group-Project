package com.example.seaSideEscape.model;

import java.math.BigDecimal;

public interface PaymentProcess {
    Payment processPayment(Long id, String paymentMethod, String billingAddress, BigDecimal amount, boolean isRoomPayment);
}
