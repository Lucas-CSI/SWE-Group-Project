package com.example.seaSideEscape.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Represents a booking that links a reservation to a specific room in the SeaSide Escape application.
 * The Booking class models the relationship between a reservation and a room.
 */
@Entity
public class Booking {

    /**
     * The unique identifier for the booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The reservation associated with the booking.
     * This is a many-to-one relationship, as multiple bookings can be linked to a single reservation.
     */
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    @JsonBackReference
    private Reservation reservation;

    /**
     * The room associated with the booking.
     * This is a many-to-one relationship, as multiple bookings can be linked to a single room.
     */
    @ManyToOne
    private Room room;

    /**
     * Default constructor for the Booking class.
     * Initializes the reservation and room fields to null.
     */
    public Booking() {
        reservation = null;
        room = null;
    }

    /**
     * Constructs a Booking with the specified reservation and room.
     *
     * @param reservation the reservation associated with the booking.
     * @param room the room associated with the booking.
     */
    public Booking(Reservation reservation, Room room) {
        this.reservation = reservation;
        this.room = room;
    }

    /**
     * Gets the reservation associated with the booking.
     *
     * @return the reservation.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation associated with the booking.
     *
     * @param reservation the reservation to associate with the booking.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the room associated with the booking.
     *
     * @return the room.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room associated with the booking.
     *
     * @param room the room to associate with the booking.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the unique identifier for the booking.
     *
     * @return the booking ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the booking.
     *
     * @param id the new booking ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
