package com.example.seaSideEscape.controller;


import com.example.seaSideEscape.service.EventBookingService;
import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventBookingController {

    @Autowired
    private EventBookingService eventBookingService;

    @Autowired
    private VenueRepository venueRepository;

    @PostMapping("/book")
    public EventBooking bookEvent(@RequestParam Long venueId,
                                  @RequestParam String eventDate,
                                  @RequestParam String eventName,
                                  @RequestParam String guestEmail){
        System.out.println("works.");
        System.out.println(eventDate);
        return eventBookingService.bookEvent(venueId, LocalDateTime.parse(eventDate), eventName, guestEmail);
    }

    @GetMapping("/venues/floor/{floorNumber}")
    public List<Venue> getAvailableVenuesByFloor(@PathVariable int floorNumber) {
        return eventBookingService.getAvailableVenuesByFloor(floorNumber);
    }

    @GetMapping("/book/{id}")
    public EventBooking getEventBooking(@PathVariable Long id) {
        return eventBookingService.getEventBooking(id);
    }
}
