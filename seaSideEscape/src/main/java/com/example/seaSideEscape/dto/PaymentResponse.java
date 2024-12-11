package com.example.seaSideEscape.dto;

import com.example.seaSideEscape.model.Payment;

/**
 * Data Transfer Object (DTO) for returning payment response details in the SeaSide Escape application.
 * This DTO encapsulates information about a completed payment and the associated event booking ID.
 */
public class PaymentResponse {

    /**
     * The ID of the event booking associated with the payment.
     */
    private Long eventBookingId;

    /**
     * The payment details for the transaction.
     */
    private Payment payment;

    /**
     * Constructs a new {@code PaymentResponse} with the given payment and event booking ID.
     *
     * @param payment        the payment details for the transaction.
     * @param eventBookingId the ID of the event booking associated with the payment.
     */
    public PaymentResponse(Payment payment, Long eventBookingId) {
        this.payment = payment;
        this.eventBookingId = eventBookingId;
    }

    /**
     * Gets the ID of the event booking associated with the payment.
     *
     * @return the event booking ID.
     */
    public Long getEventBookingId() {
        return eventBookingId;
    }

    /**
     * Gets the payment details for the transaction.
     *
     * @return the {@link Payment} object containing payment details.
     */
    public Payment getPayment() {
        return payment;
    }
}
