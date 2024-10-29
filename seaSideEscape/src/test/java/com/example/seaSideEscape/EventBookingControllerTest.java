package com.example.seaSideEscape;

import com.example.seaSideEscape.Service.EventBookingService;
import com.example.seaSideEscape.controller.EventBookingController;
import com.example.seaSideEscape.model.EventBooking;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventBookingController.class)
public class EventBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventBookingService eventBookingService;

    @InjectMocks
    private EventBookingController eventBookingController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBookEvent() throws Exception {
        // Mock booking data
        LocalDateTime eventDate = LocalDateTime.now().plusDays(2);
        EventBooking mockBooking = new EventBooking();
        mockBooking.setId(1L);
        mockBooking.setEventName("Conference");
        mockBooking.setEventDate(eventDate);

        when(eventBookingService.bookEvent(1L, eventDate, "Conference", "guest@example.com"))
                .thenReturn(mockBooking);

        mockMvc.perform(post("/events/book")
                        .param("venueId", "1")
                        .param("eventDate", eventDate.toString())
                        .param("eventName", "Conference")
                        .param("guestEmail", "guest@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Conference"))
                .andExpect(jsonPath("$.eventDate").value(eventDate.toString()));
    }

    @Test
    void testGetAvailableVenues() throws Exception {
        mockMvc.perform(get("/events/venues"))
                .andExpect(status().isOk());
    }
}
