package com.example.seaSideEscape.model;
import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({ "reservation", "roomRate", "charges", "taxes", "totalAmount", "finalAmount" })
public class Bill extends Reservation{

    private Reservation reservation;
    private BigDecimal discounts;
    private BigDecimal finalAmount;

    public Reservation getReservation() {return reservation;}

    public void setReservation(Reservation reservation) {this.reservation = reservation;}

    public BigDecimal getDiscounts() {return discounts;}

    public void setDiscounts(BigDecimal discounts) {this.discounts = discounts;}

    public BigDecimal getFinalAmount() {return finalAmount;}

    public void setFinalAmount(BigDecimal finalAmount) {this.finalAmount = finalAmount;}

}
