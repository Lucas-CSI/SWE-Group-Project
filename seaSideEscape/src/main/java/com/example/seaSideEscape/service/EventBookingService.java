package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing event bookings and venue availability in the SeaSide Escape application.
 * Provides functionality to book events, retrieve event bookings, and check venue availability.
 */
@Service
public class EventBookingService {

    private final VenueRepository venueRepository;
    private final EventBookingRepository eventBookingRepository;

    /**
     * Constructs a new {@code EventBookingService}.
     *
     * @param venueRepository the repository for managing venue entities
     * @param eventBookingRepository the repository for managing event bookings
     */
    @Autowired
    public EventBookingService(VenueRepository venueRepository, EventBookingRepository eventBookingRepository) {
        this.venueRepository = venueRepository;
        this.eventBookingRepository = eventBookingRepository;
    }

    /**
     * Books an event at a specified venue on a specific date.
     *
     * @param venueID the ID of the venue to book
     * @param eventDate the date and time of the event
     * @param eventName the name of the event
     * @param guestEmail the email address of the guest booking the event
     * @return the {@code EventBooking} object representing the booked event
     * @throws IllegalArgumentException if the venue does not exist, is already booked,
     *                                  or if the event date, name, or guest email are invalid
     */
    public EventBooking bookEvent(Long venueID, LocalDateTime eventDate, String eventName, String guestEmail) {
        Venue venue = venueRepository.findById(venueID)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found"));

        if (eventDate == null) {
            throw new IllegalArgumentException("Event Date cannot be null");
        }
        if (eventName == null || eventName.isEmpty()) {
            throw new IllegalArgumentException("Event Name cannot be null or empty");
        }
        if (guestEmail == null || guestEmail.isEmpty()) {
            throw new IllegalArgumentException("Guest Email cannot be null or empty");
        }

        if (venue.isBooked()) {
            throw new IllegalArgumentException("Venue is already booked.");
        }

        List<EventBooking> existingEvents = eventBookingRepository.findByEventDate(eventDate);
        if (!existingEvents.isEmpty()) {
            Optional<EventBooking> isBooked = existingEvents.stream()
                    .filter(event -> event.getEventDate().isEqual(eventDate))
                    .findFirst();
            if (isBooked.isPresent())
                throw new IllegalArgumentException("Date conflict with another event.");
        }

        venue.setBooked(true);
        venueRepository.save(venue);

        EventBooking eventBooking = new EventBooking();
        eventBooking.setVenue(venue);
        eventBooking.setEventDate(eventDate);
        eventBooking.setEventName(eventName);
        eventBooking.setGuestEmail(guestEmail);

        return eventBookingRepository.save(eventBooking);
    }

    /**
     * Retrieves an event booking by its ID.
     *
     * @param id the ID of the event booking
     * @return the {@code EventBooking} object representing the booking
     * @throws IllegalArgumentException if no booking is found with the specified ID
     */
    public EventBooking getEventBooking(Long id) {
        return eventBookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    /**
     * Retrieves a list of available venues on a specific floor.
     * A venue is considered available if it is not booked.
     *
     * @param floorNumber the floor number to filter available venues
     * @return a list of available venues on the specified floor
     */
    public List<Venue> getAvailableVenuesByFloor(int floorNumber) {
        return venueRepository.findByIsBookedAndFloorNumber(false, floorNumber);
    }
}
