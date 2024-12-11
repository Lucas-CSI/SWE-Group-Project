package com.example.seaSideEscape.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for processing payment requests in the SeaSide Escape application.
 * This DTO is used to capture payment details for both room and event bookings.
 */
public class BookingPaymentRequest {

    /**
     * The reservation ID associated with the room payment.
     */
    private Long reservationId;

    /**
     * The event booking ID associated with the event or venue payment.
     */
    private Long eventBookingId;

    /**
     * The method of payment (e.g., credit card, PayPal).
     */
    private String paymentMethod;

    /**
     * The billing address for the payment.
     */
    private String billingAddress;

    /**
     * The payment amount.
     */
    private double amount;

    /**
     * The credit card number used for the payment (if applicable).
     */
    private String cardNumber;

    /**
     * The expiration date of the credit card (if applicable).
     */
    private String expirationDate;

    /**
     * The CVV (Card Verification Value) of the credit card (if applicable).
     */
    private String cvv;

    /**
     * The date and time of the event associated with the booking.
     */
    private LocalDateTime eventDate;

    /**
     * The name of the event being booked.
     */
    private String eventName;

    /**
     * The email address of the guest making the payment.
     */
    private String guestEmail;

    /**
     * The venue ID associated with the event booking.
     */
    private Long venueId;

    // Getters and Setters

    /**
     * Gets the venue ID associated with the event booking.
     *
     * @return the venue ID.
     */
    public Long getVenueId() {
        return venueId;
    }

    /**
     * Sets the venue ID associated with the event booking.
     *
     * @param venueId the venue ID to set.
     */
    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    /**
     * Gets the date and time of the event.
     *
     * @return the event date and time.
     */
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    /**
     * Sets the date and time of the event.
     *
     * @param eventDate the event date and time to set.
     */
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Gets the name of the event.
     *
     * @return the event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event.
     *
     * @param eventName the event name to set.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the email address of the guest making the payment.
     *
     * @return the guest email address.
     */
    public String getGuestEmail() {
        return guestEmail;
    }

    /**
     * Sets the email address of the guest making the payment.
     *
     * @param guestEmail the guest email address to set.
     */
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    /**
     * Gets the reservation ID for the room payment.
     *
     * @return the reservation ID.
     */
    public Long getReservationId() {
        return reservationId;
    }

    /**
     * Sets the reservation ID for the room payment.
     *
     * @param reservationId the reservation ID to set.
     */
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * Gets the event booking ID for the event or venue payment.
     *
     * @return the event booking ID.
     */
    public Long getEventBookingId() {
        return eventBookingId;
    }

    /**
     * Sets the event booking ID for the event or venue payment.
     *
     * @param eventBookingId the event booking ID to set.
     */
    public void setEventBookingId(Long eventBookingId) {
        this.eventBookingId = eventBookingId;
    }

    /**
     * Gets the payment method.
     *
     * @return the payment method.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method.
     *
     * @param paymentMethod the payment method to set.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the billing address for the payment.
     *
     * @return the billing address.
     */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address for the payment.
     *
     * @param billingAddress the billing address to set.
     */
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Gets the payment amount.
     *
     * @return the payment amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the payment amount.
     *
     * @param amount the payment amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the credit card number used for the payment.
     *
     * @return the credit card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the credit card number used for the payment.
     *
     * @param cardNumber the credit card number to set.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the expiration date of the credit card.
     *
     * @return the expiration date.
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the credit card.
     *
     * @param expirationDate the expiration date to set.
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the CVV of the credit card.
     *
     * @return the CVV.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV of the credit card.
     *
     * @param cvv the CVV to set.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
