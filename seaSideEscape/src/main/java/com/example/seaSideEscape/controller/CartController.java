package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.BookingService;
import com.example.seaSideEscape.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final RoomService roomService;
    private final AccountService accountService;

    @Autowired
    public CartController(RoomService roomService, AccountService accountService, RoomRepository roomRepository) {
        this.accountService = accountService;
        this.roomService = roomService;
    }

    @GetMapping("/subtotal")
    public ResponseEntity<Map<String, Double>> getCartSubtotal(@CookieValue("username") String username) {
        double subtotal = roomService.calculateTotalCartCost(username);
        Map<String, Double> response = new HashMap<>();
        response.put("subtotal", subtotal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove/{roomId}")
    public ResponseEntity<String> removeRoomFromCart(@CookieValue("username") String username, @PathVariable Long roomId) {
        // Fetch the account associated with the username
        Optional<Account> account = accountService.findAccountByUsername(username);
        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }

        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }

        boolean removed = roomService.removeRoomFromCart(account.get(), room);
        if (removed) {
            return ResponseEntity.ok("Room removed from cart.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove room from cart.");
    }

}
