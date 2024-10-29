package com.example.seaSideEscape.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@JsonPropertyOrder({ "reservation", "roomRate", "charges", "taxes", "totalAmount", "finalAmount" })
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;
    private BigDecimal discounts;
    private BigDecimal subTotal;
    private BigDecimal taxes;
    private BigDecimal totalAmount;
    private BigDecimal roomRate;

    @OneToMany()
    private List<Charge> charges;

    public Reservation getReservation() {return reservation;}

    public void setReservation(Reservation reservation) {this.reservation = reservation;}

    public BigDecimal getDiscounts() {return discounts;}

    public void setDiscounts(BigDecimal discounts) {this.discounts = discounts;}

    public BigDecimal getTotalAmount() {return totalAmount;}

    public void setTotalAmount(BigDecimal totalAmount) {this.totalAmount = totalAmount;}

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }
}
