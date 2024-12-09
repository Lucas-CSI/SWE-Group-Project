package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final RoomService roomService;

    @Autowired
    public CartController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/subtotal")
    public ResponseEntity<Map<String, Double>> getCartSubtotal(@CookieValue("username") String username) {
        double subtotal = roomService.calculateTotalCartCost(username);
        Map<String, Double> response = new HashMap<>();
        response.put("subtotal", subtotal);
        return ResponseEntity.ok(response);
    }
}
