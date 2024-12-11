package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a payment in the SeaSide Escape application.
 * The Payment class models details about transactions, including the associated reservation or event,
 * payment method, amount, and status.
 */
@Entity
public class Payment {

    /**
     * The unique identifier for the payment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The reservation associated with the payment.
     * This is a many-to-one relationship, as multiple payments can be linked to a single reservation.
     */
    @ManyToOne
    private Reservation reservation;

    /**
     * The payment method used for the transaction (e.g., credit card, PayPal, etc.).
     */
    private String paymentMethod;

    /**
     * The amount of the payment.
     */
    private BigDecimal amount;

    /**
     * The date and time the payment was made.
     */
    private LocalDateTime paymentDate;

    /**
     * The billing address associated with the payment.
     */
    private String billingAddress;

    /**
     * Indicates whether the payment was successful.
     */
    private boolean success;

    /**
     * The credit card number used for the payment (if applicable).
     */
    private String cardNumber;

    /**
     * The expiration date of the credit card used for the payment.
     */
    private String expirationDate;

    /**
     * The CSV (Card Security Value) of the credit card used for the payment.
     */
    private String csv;

    /**
     * The event booking associated with the payment.
     * This is a many-to-one relationship, as multiple payments can be linked to a single event booking.
     */
    @ManyToOne
    private EventBooking eventBooking;

    /**
     * Gets the unique identifier for the payment.
     *
     * @return the payment ID.
     */
    public Long getID() {
        return id;
    }

    /**
     * Sets the unique identifier for the payment.
     *
     * @param id the new payment ID.
     */
    public void setID(Long id) {
        this.id = id;
    }

    /**
     * Gets the reservation associated with the payment.
     *
     * @return the reservation.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation associated with the payment.
     *
     * @param reservation the new reservation.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the payment method used for the transaction.
     *
     * @return the payment method.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used for the transaction.
     *
     * @param paymentMethod the new payment method.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return the payment amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount the new payment amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the date and time the payment was made.
     *
     * @return the payment date and time.
     */
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the date and time the payment was made.
     *
     * @param paymentDate the new payment date and time.
     */
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets the billing address associated with the payment.
     *
     * @return the billing address.
     */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address associated with the payment.
     *
     * @param billingAddress the new billing address.
     */
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Checks if the payment was successful.
     *
     * @return {@code true} if the payment was successful; {@code false} otherwise.
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * Sets the success status of the payment.
     *
     * @param success {@code true} if the payment was successful; {@code false} otherwise.
     */
    public void setSuccess(boolean success) {
        this.success = success;
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
     * @param cardNumber the new credit card number.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the expiration date of the credit card used for the payment.
     *
     * @return the expiration date.
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the credit card used for the payment.
     *
     * @param expirationDate the new expiration date.
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the CSV (Card Security Value) of the credit card used for the payment.
     *
     * @return the CSV.
     */
    public String getCSV() {
        return csv;
    }

    /**
     * Sets the CSV (Card Security Value) of the credit card used for the payment.
     *
     * @param csv the new CSV.
     */
    public void setCSV(String csv) {
        this.csv = csv;
    }

    /**
     * Gets the event booking associated with the payment.
     *
     * @return the event booking.
     */
    public EventBooking getEventBooking() {
        return eventBooking;
    }

    /**
     * Sets the event booking associated with the payment.
     *
     * @param eventBooking the new event booking.
     */
    public void setEventBooking(EventBooking eventBooking) {
        this.eventBooking = eventBooking;
    }
}
