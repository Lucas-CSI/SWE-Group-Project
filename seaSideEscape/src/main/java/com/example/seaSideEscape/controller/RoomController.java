package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for handling room-related requests in the SeaSide Escape application.
 * Provides endpoints for retrieving available rooms.
 */
@RestController
public class RoomController {

    private final RoomService roomService;
    private final SerializeModule<Room> serializeModule = new SerializeModule<>();

    /**
     * Constructs a new {@code RoomController}.
     *
     * @param roomService the service handling room-related operations.
     */
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Retrieves a list of available rooms for the logged-in user.
     *
     * @param username the username of the logged-in user, retrieved from cookies.
     * @return a JSON string representing the list of available rooms.
     * @throws JsonProcessingException if an error occurs during JSON serialization.
     */
    @GetMapping("/getAvailableRooms")
    public String getAvailableRooms(@CookieValue("username") String username) throws JsonProcessingException {
        return serializeModule.listToJSON(roomService.getAvailableRooms(username));
    }
}
