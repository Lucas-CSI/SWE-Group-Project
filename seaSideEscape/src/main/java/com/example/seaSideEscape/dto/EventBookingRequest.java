package com.example.seaSideEscape.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for creating event booking requests in the SeaSide Escape application.
 * This DTO captures the necessary information for booking an event at a specified venue.
 */
public class EventBookingRequest {

    /**
     * The ID of the venue where the event is to be held.
     */
    private Long venueId;

    /**
     * The name of the event being booked.
     */
    private String eventName;

    /**
     * The email address of the guest making the booking.
     */
    private String guestEmail;

    /**
     * The date and time of the event.
     */
    private LocalDateTime eventDate;

    // Getters and setters

    /**
     * Gets the ID of the venue where the event is to be held.
     *
     * @return the venue ID.
     */
    public Long getVenueId() {
        return venueId;
    }

    /**
     * Sets the ID of the venue where the event is to be held.
     *
     * @param venueId the venue ID to set.
     */
    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    /**
     * Gets the name of the event being booked.
     *
     * @return the event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event being booked.
     *
     * @param eventName the event name to set.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the email address of the guest making the booking.
     *
     * @return the guest's email address.
     */
    public String getGuestEmail() {
        return guestEmail;
    }

    /**
     * Sets the email address of the guest making the booking.
     *
     * @param guestEmail the guest's email address to set.
     */
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
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
     * @param eventDate the event date and time to set.
     */
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
