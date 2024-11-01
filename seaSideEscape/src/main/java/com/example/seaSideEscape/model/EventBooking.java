package com.example.seaSideEscape.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EventBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    private Venue venue;

    private LocalDateTime eventDate;
    private String eventName, guestEmail;
    
    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public Venue getVenue(){return venue;}

    public void setVenue(Venue venue){this.venue = venue;}

    public LocalDateTime getEventDate(){return  eventDate;}

    public void setEventDate(LocalDateTime eventDate){this.eventDate = eventDate;}

    public String getEventName(){return eventName;}

    public void setEventName(String eventName){this.eventName = eventName;}

    public String getGuestEmail(){return guestEmail;}

    public void setGuestEmail(String guestEmail){this.guestEmail = guestEmail;}
}
