package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a booking for an event at a specific venue in the SeaSide Escape application.
 * The EventBooking class includes details such as the venue, event date, event name, guest information, and payment status.
 */
@Entity
public class EventBooking {

    /**
     * The unique identifier for the event booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The venue where the event will take place.
     * This is a many-to-one relationship, as multiple events can be booked at the same venue.
     */
    @ManyToOne
    private Venue venue;

    /**
     * The date and time of the event.
     */
    private LocalDateTime eventDate;

    /**
     * The name of the event being booked.
     */
    private String eventName;

    /**
     * The email address of the guest who made the booking.
     */
    private String guestEmail;

    /**
     * Indicates whether the booking has been paid for.
     */
    private boolean paid;

    /**
     * Gets the unique identifier for the event booking.
     *
     * @return the event booking ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the event booking.
     *
     * @param id the new event booking ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the venue where the event will take place.
     *
     * @return the venue.
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * Sets the venue where the event will take place.
     *
     * @param venue the new venue.
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * Gets the date and time of the event.
     *
     * @return the event date and time.
     */
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    /**
     * Sets the date and time of the event.
     *
     * @param eventDate the new event date and time.
     */
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Gets the name of the event.
     *
     * @return the event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event.
     *
     * @param eventName the new event name.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the email address of the guest who made the booking.
     *
     * @return the guest's email address.
     */
    public String getGuestEmail() {
        return guestEmail;
    }

    /**
     * Sets the email address of the guest who made the booking.
     *
     * @param guestEmail the new guest email address.
     */
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    /**
     * Checks if the booking has been paid for.
     *
     * @return {@code true} if the booking is paid; {@code false} otherwise.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the payment status of the booking.
     *
     * @param paid {@code true} if the booking is paid; {@code false} otherwise.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
