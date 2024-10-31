package com.example.seaSideEscape.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Room> rooms;

    @ManyToOne
    private Guest guest;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal roomRate;
    private BigDecimal discount;
    private boolean paid;

    @OneToMany
    private List<Charge> charges;

    public Long getId(){return id;}

    public BigDecimal getDiscount(){return discount;}

    public void setDiscount(BigDecimal discount){this.discount = discount;}

    public void setId(Long id){this.id = id;}

    public List<Room> getRooms(){return rooms;}

    public void clearRooms(){ rooms.clear();}

    public void addRoom(Room room){this.rooms.add(room);}

    public Guest getGuest(){return guest;}

    public void setGuest(Guest guest){this.guest = guest;}

    public LocalDate getStartDate(){return startDate;}

    public void setStartDate(LocalDate startDate){this.startDate = startDate;}

    public LocalDate getEndDate(){return endDate;}

    public void setEndDate(LocalDate endDate){this.endDate = endDate;}

    public BigDecimal getRoomRate() {return roomRate;}

    public void setRoomRate(BigDecimal roomRate) {this.roomRate = roomRate;}

    public boolean isPaid() {return paid;}

    public void setPaid(boolean paid) {this.paid = paid;}

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }
}
