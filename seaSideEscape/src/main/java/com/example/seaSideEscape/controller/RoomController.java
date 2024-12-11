package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    private final RoomService roomService;
    private final SerializeModule<Room> serializeModule = new SerializeModule<>();

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/getAvailableRooms")
    public String getAvailableRooms(@CookieValue("username") String username) throws JsonProcessingException {
        return serializeModule.listToJSON(roomService.getAvailableRooms(username));
    }
}
