package com.example.seaSideEscape.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Guest guest;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal roomRate;
    private BigDecimal taxes;
    private BigDecimal totalAmount;
    private BigDecimal discount;

    @OneToMany(mappedBy = "reservation")
    private List<Charge> charges;

    public Long getId(){return id;}

    public BigDecimal getDiscount(){return discount;}

    public void setDiscount(BigDecimal discount){this.discount = discount;}

    public void setId(Long id){this.id = id;}

    public Room getRoom(){return room;}

    public void setRoom(Room room){this.room = room;}

    public Guest getGuest(){return guest;}

    public void setGuest(Guest guest){this.guest = guest;}

    public LocalDate getStartDate(){return startDate;}

    public void setStartDate(LocalDate startDate){this.startDate = startDate;}

    public LocalDate getEndDate(){return endDate;}

    public void setEndDate(LocalDate endDate){this.endDate = endDate;}

    public BigDecimal getRoomRate() {return roomRate;}

    public void setRoomRate(BigDecimal roomRate) {this.roomRate = roomRate;}

    public BigDecimal getTaxes() {return taxes;}

    public void setTaxes(BigDecimal taxes) {this.taxes = taxes;}

    public BigDecimal getTotalAmount() {return totalAmount;}

    public void setTotalAmount(BigDecimal totalAmount) {this.totalAmount = totalAmount;}

    public List<Charge> getCharges() {return charges;}

    public void setCharges(List<Charge> charges) {this.charges = charges;}

}