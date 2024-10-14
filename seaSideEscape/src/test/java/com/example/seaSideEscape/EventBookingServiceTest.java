package com.example.seaSideEscape;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.seaSideEscape.model.EventBooking;
import com.example.seaSideEscape.model.Venue;
import com.example.seaSideEscape.repository.EventBookingRepository;
import com.example.seaSideEscape.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventBookingServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private EventBookingRepository eventBookingRepository;

    @InjectMocks
    private EventBookingService eventBookingService;

    private Venue venue;
    private LocalDateTime eventDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Venue
        venue = new Venue();
        venue.setId(1L);
        venue.setName("Conference Room");
        venue.setLocation("First Floor");
        venue.setCapacity(50);
        venue.setBooked(false);

        // Mock event date
        eventDate = LocalDateTime.now().plusDays(2);
    }

    @Test
    void testBookEventSuccess() {
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(eventBookingRepository.findByEventDate(eventDate)).thenReturn(List.of());

        EventBooking booking = eventBookingService.bookEvent(1L, eventDate, "Tech Conference", "guest@example.com");

        assertNotNull(booking);
        assertEquals("Tech Conference", booking.getEventName());
        assertEquals(venue, booking.getVenue());
        assertEquals(eventDate, booking.getEventDate());

        // Verify that the venue was marked as booked
        verify(venueRepository, times(1)).save(venue);
        assertTrue(venue.isBooked());
    }

    @Test
    void testBookEventVenueAlreadyBooked() {
        venue.setBooked(true);
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventBookingService.bookEvent(1L, eventDate, "Tech Conference", "guest@example.com");
        });

        assertEquals("Venue is already booked.", exception.getMessage());
    }

    @Test
    void testBookEventDateConflict() {
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(eventBookingRepository.findByEventDate(eventDate)).thenReturn(List.of(new EventBooking()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventBookingService.bookEvent(1L, eventDate, "Tech Conference", "guest@example.com");
        });

        assertEquals("Date conflict with another event.", exception.getMessage());
    }
}
