package com.example.seaSideEscape.model;

import java.math.BigDecimal;

/**
 * Interface for processing payments in the SeaSide Escape application.
 * The PaymentProcess interface defines the method required to handle payments for reservations or event bookings.
 */
public interface PaymentProcess {

    /**
     * Processes a payment for a reservation or event booking.
     *
     * @param reservationOrBookingId the ID of the reservation or event booking for which the payment is being processed.
     * @param paymentMethod          the method of payment (e.g., credit card, PayPal).
     * @param billingAddress         the billing address associated with the payment.
     * @param amount                 the amount to be charged.
     * @param cardNumber             the credit card number (if applicable).
     * @param expirationDate         the expiration date of the credit card (if applicable).
     * @param cvv                    the CVV (Card Verification Value) of the credit card (if applicable).
     * @return a {@link Payment} object representing the processed payment.
     */
    Payment processPayment(Long reservationOrBookingId, String paymentMethod, String billingAddress, BigDecimal amount,
                           String cardNumber, String expirationDate, String cvv);
}
