package com.example.seaSideEscape.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a reservation in the SeaSide Escape application.
 * The Reservation class models details about a guest's stay, including bookings, charges, and payment status.
 */
@Entity
public class Reservation {

    /**
     * The unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The list of bookings associated with the reservation.
     * This is a one-to-many relationship managed by the {@link Booking} class.
     */
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Booking> bookings;

    /**
     * The account associated with the reservation.
     * This is a many-to-one relationship, as multiple reservations can belong to a single account.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = true)
    @JsonBackReference
    private Account account;

    /**
     * The check-in date for the reservation.
     */
    private LocalDate checkInDate;

    /**
     * The check-out date for the reservation.
     */
    private LocalDate checkOutDate;

    /**
     * The room rate for the reservation.
     */
    private BigDecimal roomRate;

    /**
     * The discount applied to the reservation.
     */
    private BigDecimal discount;

    /**
     * Indicates whether the reservation has been paid.
     */
    private boolean paid;

    /**
     * Indicates whether the reservation has been booked.
     */
    private boolean booked;

    /**
     * Indicates whether the guest has checked in for the reservation.
     */
    private boolean checkedIn;

    /**
     * The list of charges associated with the reservation.
     */
    @OneToMany
    private List<Charge> charges;

    /**
     * Gets the unique identifier for the reservation.
     *
     * @return the reservation ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the reservation.
     *
     * @param id the new reservation ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the list of bookings associated with the reservation.
     *
     * @return the list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings associated with the reservation.
     *
     * @param bookings the new list of bookings.
     */
    public void setBookingList(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a booking to the reservation.
     *
     * @param booking the booking to be added.
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    /**
     * Gets the account associated with the reservation.
     *
     * @return the account.
     */
    public Account getGuest() {
        return account;
    }

    /**
     * Sets the account associated with the reservation.
     *
     * @param guest the new account.
     */
    public void setGuest(Account guest) {
        this.account = guest;
    }

    /**
     * Gets the check-in date for the reservation.
     *
     * @return the check-in date.
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * Sets the check-in date for the reservation.
     *
     * @param checkInDate the new check-in date.
     */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Gets the check-out date for the reservation.
     *
     * @return the check-out date.
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Sets the check-out date for the reservation.
     *
     * @param checkOutDate the new check-out date.
     */
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * Gets the room rate for the reservation.
     *
     * @return the room rate.
     */
    public BigDecimal getRoomRate() {
        return roomRate;
    }

    /**
     * Sets the room rate for the reservation.
     *
     * @param roomRate the new room rate.
     */
    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }

    /**
     * Gets the discount applied to the reservation.
     *
     * @return the discount.
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * Sets the discount applied to the reservation.
     *
     * @param discount the new discount.
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * Checks if the reservation has been paid.
     *
     * @return {@code true} if paid; {@code false} otherwise.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the payment status of the reservation.
     *
     * @param paid {@code true} if paid; {@code false} otherwise.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Checks if the reservation has been booked.
     *
     * @return {@code true} if booked; {@code false} otherwise.
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Sets the booking status of the reservation.
     *
     * @param booked {@code true} if booked; {@code false} otherwise.
     */
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    /**
     * Checks if the guest has checked in for the reservation.
     *
     * @return {@code true} if checked in; {@code false} otherwise.
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Sets the check-in status for the reservation.
     *
     * @param checkedIn {@code true} if checked in; {@code false} otherwise.
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * Gets the list of charges associated with the reservation.
     *
     * @return the list of charges.
     */
    public List<Charge> getCharges() {
        return charges;
    }

    /**
     * Sets the list of charges associated with the reservation.
     *
     * @param charges the new list of charges.
     */
    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    /**
     * Enum to sort reservations by their start date.
     */
    public enum Sort implements Comparator<Reservation> {
        StartDate {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                Long date1 = o1.getCheckInDate().toEpochDay();
                Long date2 = o2.getCheckOutDate().toEpochDay();
                if (!date1.equals(date2)) {
                    return o1.getCheckInDate().compareTo(o2.getCheckInDate());
                } else {
                    return o1.getId().compareTo(o2.getId());
                }
            }
        }
    }
}
