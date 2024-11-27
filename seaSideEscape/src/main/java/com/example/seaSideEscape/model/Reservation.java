package com.example.seaSideEscape.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Booking> bookings;

    @ManyToOne
    private Account account;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal roomRate;
    private BigDecimal discount;
    private boolean paid;

    @OneToMany
    private List<Charge> charges;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setRoomList(List<Booking> booking) {
        this.bookings = booking;
    }

    public void addRoom(Booking booking){
        this.bookings.add(booking);
    }

    public Long getId(){return id;}

    public BigDecimal getDiscount(){return discount;}

    public void setDiscount(BigDecimal discount){this.discount = discount;}

    public void setId(Long id){this.id = id;}

    public Account getGuest(){return account;}

    public void setGuest(Account guest){this.account = guest;}

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

    public enum Sort implements Comparator<Reservation> {
        StartDate {
            public int compare(Reservation o1, Reservation o2) {
                Long date1 = o1.getStartDate().toEpochDay();
                Long date2 = o2.getEndDate().toEpochDay();
                if(!date1.equals(date2))
                    return o1.getStartDate().compareTo(o2.getStartDate());
                else
                    return o1.getId().compareTo(o2.getId());
            }
        }
    }
}
