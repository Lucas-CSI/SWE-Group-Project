package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.dto.EventBookingRequest;
import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.service.EmailService;
import com.example.seaSideEscape.service.EventBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles event booking-related operations in the SeaSide Escape application.
 * The EventBookingController provides endpoints for booking events, retrieving booking details,
 * and querying available venues.
 */
@RestController
@RequestMapping("/events")
public class EventBookingController {

    @Autowired
    private EventBookingService eventBookingService;

    @Autowired
    private EmailService emailService;

    /**
     * Books an event at a specified venue.
     *
     * @param eventBookingRequest the request containing event booking details such as venue ID, event date,
     *                            event name, and guest email.
     * @return the created {@link EventBooking} object.
     */
    @PostMapping("/book")
    public EventBooking bookEvent(@RequestBody EventBookingRequest eventBookingRequest) {
        EventBooking bookedEvent = eventBookingService.bookEvent(
                eventBookingRequest.getVenueId(),
                eventBookingRequest.getEventDate(),
                eventBookingRequest.getEventName(),
                eventBookingRequest.getGuestEmail()
        );

        emailService.sendConfirmationEmail(
                bookedEvent.getGuestEmail(),
                bookedEvent.getEventName(),
                bookedEvent.getEventDate(),
                bookedEvent.getVenue()
        );

        return bookedEvent;
    }

    /**
     * Retrieves a list of available venues on a specified floor.
     *
     * @param floorNumber the floor number to query for available venues.
     * @return a list of {@link Venue} objects representing available venues on the specified floor.
     */
    @GetMapping("/venues/floor/{floorNumber}")
    public List<Venue> getAvailableVenuesByFloor(@PathVariable int floorNumber) {
        return eventBookingService.getAvailableVenuesByFloor(floorNumber);
    }

    /**
     * Retrieves details of a specific event booking by its ID.
     *
     * @param id the ID of the event booking to retrieve.
     * @return the {@link EventBooking} object associated with the specified ID.
     */
    @GetMapping("/book/{id}")
    public EventBooking getEventBooking(@PathVariable Long id) {
        return eventBookingService.getEventBooking(id);
    }
}
