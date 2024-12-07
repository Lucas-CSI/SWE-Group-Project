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
    private boolean finalized;


    @ManyToOne
    private Reservation reservation;

    public Long getId(){return id;}

    public boolean isFinalized() { return finalized; }
    public void setFinalized(boolean finalized) { this.finalized = finalized; }

    public void setId(Long id){this.id = id;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

    public BigDecimal getAmount(){return amount;}

    public void setAmount(BigDecimal amount){this.amount = amount;}

    public Reservation getReservation(){return reservation;}

    public void setReservation(Reservation reservation){this.reservation = reservation;}
}
