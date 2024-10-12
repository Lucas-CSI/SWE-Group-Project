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

    public Reservation getReservation(){return reservation;}

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
}

