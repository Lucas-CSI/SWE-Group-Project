package com.example.seaSideEscape.model;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Reservation reservation;

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

    public BigDecimal getAmount(){return amount;}

    public void setAmount(BigDecimal amount){this.amount = amount;}

    public Reservation getReservation(){return reservation;}

    public void setReservation(Reservation reservation){this.reservation = reservation;}
}