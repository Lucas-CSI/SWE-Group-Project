package com.example.seaSideEscape;


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
                                  @RequestParam LocalDateTime eventDate,
                                  @RequestParam String eventName,
                                  @RequestParam String guestEmail){
        return eventBookingService.bookEvent(venueId, eventDate, eventName, guestEmail);


    }
    @GetMapping("/venues")
    public List<Venue> getAvailableVenues(){
        return venueRepository.findByIsBooked(false);
    }

    @GetMapping("/book/{id}")
    public EventBooking getEventBooking(@PathVariable Long id) {
        return eventBookingService.getEventBooking(id);
    }


}
