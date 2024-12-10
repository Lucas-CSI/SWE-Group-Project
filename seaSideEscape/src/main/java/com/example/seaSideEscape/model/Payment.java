package com.example.seaSideEscape.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    private String paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String billingAddress;
    private boolean success;

    private String cardNumber;
    private String expirationDate;
    private String csv;

    @ManyToOne
    private EventBooking eventBooking;

    public Long getID(){return id;}

    public void setID(Long id){this.id = id;}

    public Reservation getReservation(){return reservation;}

    public String getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate(){ return expirationDate;}

    public void setExpirationDate(String expirationDate){ this.expirationDate = expirationDate;}

    public String getCSV(){return csv;}

    public void setCSV(String csv){this.csv = csv;}

    public void setReservation(Reservation reservation){this.reservation = reservation;}

    public String getPaymentMethod(){return paymentMethod;}

    public void setPaymentMethod(String paymentMethod){this.paymentMethod = paymentMethod;}

    public BigDecimal getAmount(){return amount;}

    public void setAmount(BigDecimal amount){this.amount = amount;}

    public LocalDateTime getPaymentDate(){return paymentDate;}

    public void setPaymentDate(LocalDateTime paymentDate){this.paymentDate = paymentDate;}

    public String getBillingAddress(){return billingAddress;}

    public void setBillingAddress(String billingAddress){this.billingAddress = billingAddress;}

    public boolean getSuccess(){return success;}

    public void setSuccess(boolean success){this.success = success;}

    public EventBooking getEventBooking() {
        return eventBooking;
    }

    public void setEventBooking(EventBooking eventBooking) {
        this.eventBooking = eventBooking;
    }
}

