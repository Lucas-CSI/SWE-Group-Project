package com.example.seaSideEscape.dto;

import com.example.seaSideEscape.model.Payment;

public class PaymentResponse {
    private Long eventBookingId;
    private Payment payment;

    public PaymentResponse(Payment payment, Long eventBookingId) {
        this.payment = payment;
        this.eventBookingId = eventBookingId;
    }

    public Long getEventBookingId() {
        return eventBookingId;
    }

    public Payment getPayment() {
        return payment;
    }
}
