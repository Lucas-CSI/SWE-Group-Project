package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms(@RequestParam("checkInDate")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate, @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        return roomService.getAvailableRooms(checkInDate, checkOutDate);
    }
}
