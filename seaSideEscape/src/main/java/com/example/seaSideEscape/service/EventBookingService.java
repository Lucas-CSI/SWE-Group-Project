package com.example.seaSideEscape.Service;


import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventBookingService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventBookingRepository eventBookingRepository;

    @Autowired
    public EventBookingService(VenueRepository venueRepository, EventBookingRepository eventBookingRepository){
        this.venueRepository = venueRepository;
        this.eventBookingRepository = eventBookingRepository;
    }

    public EventBooking bookEvent(Long venueID, LocalDateTime eventDate, String eventName, String guestEmail){
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
                    .filter(event -> event.getEventDate() == eventDate)
                    .findFirst();
            if(isBooked.isPresent())
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

    public EventBooking getEventBooking(Long id) {
        return eventBookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<Venue> getAvailableVenuesByFloor(int floorNumber) {
        return venueRepository.findByIsBookedAndFloorNumber(false, floorNumber);
    }
}
