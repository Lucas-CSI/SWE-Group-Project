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

/**
 * Handles operations related to hotels and messages in the SeaSide Escape application.
 * The HotelController provides endpoints for interacting with hotel data and testing message handling.
 */
@RestController
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    private List<String> messages = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(HotelController.class.getName());

    /**
     * Retrieves a list of messages.
     * This endpoint is primarily for testing purposes.
     *
     * @return a list of messages.
     */
    @GetMapping("/hello")
    public List<String> getMessages() {
        LOGGER.info("Fetching messages...");
        return messages;
    }

    /**
     * Adds a new message to the list.
     * This endpoint is primarily for testing purposes.
     *
     * @param content the content of the message to add.
     * @return the updated list of messages.
     */
    @PostMapping("/hello")
    public List<String> addMessage(@RequestBody String content) {
        LOGGER.info("Adding message: " + content);
        messages.add(content);
        return messages;
    }

    /**
     * Retrieves all hotels from the database.
     * This endpoint is for testing database operations.
     *
     * @return a list of {@link Hotel} objects.
     */
    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * Adds a new hotel to the database.
     * This endpoint is for testing database operations.
     *
     * @param hotel the {@link Hotel} object to add.
     * @return the saved {@link Hotel} object.
     */
    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
