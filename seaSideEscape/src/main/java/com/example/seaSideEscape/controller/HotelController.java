package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Hotel;
import com.example.seaSideEscape.repository.HotelRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;


    private List<String> messages = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(HotelController.class.getName());

    @GetMapping("/hello")
    public List<String> getMessages() {
        LOGGER.info("Fetching messages...");
        return messages;
    }

    @PostMapping("/hello")
    public List<String> addMessage(@RequestBody String content) {
        LOGGER.info("Adding message: " + content);
        messages.add(content);
        return messages;
    }

    //Testing a database
    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }


    //Testing adding hotels to a database
    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

}