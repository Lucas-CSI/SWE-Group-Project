package com.example.seaSideEscape.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingPaymentRequest {

    private Long reservationId;        // For room payments
    private Long eventBookingId;       // For event/venue payments
    private String paymentMethod;
    private String billingAddress;
    private BigDecimal amount;
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    // Getters and Setters
    private LocalDateTime eventDate;
    private String eventName;
    private String guestEmail;
    private Long venueId;

    public Long getVenueId(){return venueId;}

    public void setVenueId(Long venueId){this.venueId = venueId;}

    public LocalDateTime getEventDate(){return  eventDate;}

    public void setEventDate(LocalDateTime eventDate){this.eventDate = eventDate;}

    public String getEventName(){return eventName;}

    public void setEventName(String eventName){this.eventName = eventName;}

    public String getGuestEmail(){return guestEmail;}

    public void setGuestEmail(String guestEmail){this.guestEmail = guestEmail;}

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getEventBookingId() {
        return eventBookingId;
    }


    public void setEventBookingId(Long eventBookingId) {
        this.eventBookingId = eventBookingId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
