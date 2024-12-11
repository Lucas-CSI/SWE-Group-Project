package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.repository.RoomRepository;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles cart-related operations in the SeaSide Escape application.
 * The CartController provides endpoints for retrieving cart subtotals and managing rooms in a user's cart.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final RoomService roomService;
    private final AccountService accountService;

    /**
     * Constructor for the CartController.
     *
     * @param roomService   the service handling room operations.
     * @param accountService the service handling account operations.
     * @param roomRepository the repository for room data (not used directly here but may be needed in other methods).
     */
    @Autowired
    public CartController(RoomService roomService, AccountService accountService, RoomRepository roomRepository) {
        this.accountService = accountService;
        this.roomService = roomService;
    }

    /**
     * Retrieves the subtotal cost of all rooms in the user's cart.
     *
     * @param username the username of the account, retrieved from cookies.
     * @return a {@link ResponseEntity} containing a map with the subtotal or an error response if the calculation fails.
     */
    @GetMapping("/subtotal")
    public ResponseEntity<Map<String, Double>> getCartSubtotal(@CookieValue("username") String username) {
        double subtotal = roomService.calculateTotalCartCost(username);
        Map<String, Double> response = new HashMap<>();
        response.put("subtotal", subtotal);
        return ResponseEntity.ok(response);
    }

    /**
     * Removes a specific room from the user's cart.
     *
     * @param username the username of the account, retrieved from cookies.
     * @param roomId   the ID of the room to be removed from the cart.
     * @return a {@link ResponseEntity} indicating success or failure of the operation.
     */
    @PostMapping("/remove/{roomId}")
    public ResponseEntity<String> removeRoomFromCart(@CookieValue("username") String username, @PathVariable Long roomId) {
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
