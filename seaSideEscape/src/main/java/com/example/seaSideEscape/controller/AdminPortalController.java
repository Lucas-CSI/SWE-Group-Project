package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.AdminPortalService;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Handles administrative operations in the SeaSide Escape application.
 * The AdminPortalController provides endpoints for account management, permission modification,
 * and room status management.
 */
@RestController
@RequestMapping("/admin")
public class AdminPortalController {

    private final AdminPortalService adminPortalService;
    private final RoomService roomService;
    private final SerializeModule<Room> roomSerializeModule = new SerializeModule<>();

    /**
     * Constructor for the AdminPortalController.
     *
     * @param adminPortalService the service handling administrative operations.
     * @param roomService        the service handling room-related operations.
     */
    @Autowired
    public AdminPortalController(AdminPortalService adminPortalService, RoomService roomService) {
        this.adminPortalService = adminPortalService;
        this.roomService = roomService;
    }

    /**
     * Creates a new Clerk account.
     *
     * @param toCreateUsername the username for the new Clerk account.
     * @param toCreateEmail    the email address for the new Clerk account.
     * @param username         the username of the admin creating the account (retrieved from cookies).
     * @return a {@link ResponseEntity} indicating the status of the account creation.
     * @throws NoSuchAlgorithmException if an error occurs during account creation.
     */
    @PostMapping("/createAccount/clerk")
    public ResponseEntity<String> createClerkAccount(
            @RequestParam("username") String toCreateUsername,
            @RequestParam("email") String toCreateEmail,
            @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Clerk);
    }

    /**
     * Creates a new Admin account.
     *
     * @param toCreateUsername the username for the new Admin account.
     * @param toCreateEmail    the email address for the new Admin account.
     * @param username         the username of the admin creating the account (retrieved from cookies).
     * @return a {@link ResponseEntity} indicating the status of the account creation.
     * @throws NoSuchAlgorithmException if an error occurs during account creation.
     */
    @PostMapping("/createAccount/admin")
    public ResponseEntity<String> createAdminAccount(
            @RequestParam("username") String toCreateUsername,
            @RequestParam("email") String toCreateEmail,
            @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Admin);
    }

    /**
     * Changes the permission level of an account.
     *
     * @param toChangeUsername the username of the account whose permission is to be changed.
     * @param permissionLevel  the new permission level (integer representation of {@link Account.PermissionLevel}).
     * @param username         the username of the admin performing the change (retrieved from cookies).
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     * @throws NoSuchAlgorithmException if an error occurs during the permission change process.
     */
    @PostMapping("/changePermission")
    public ResponseEntity<String> changeAccountPermission(
            @RequestParam("username") String toChangeUsername,
            @RequestParam("permissionLevel") Integer permissionLevel,
            @CookieValue("username") String username) throws NoSuchAlgorithmException {
        String response = adminPortalService.modifyPermissionLevel(toChangeUsername, Account.PermissionLevel.values()[permissionLevel], username);

        if (!response.contains("Error")) {
            return ResponseEntity.ok("Successfully changed permission");
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves information about a specific room.
     *
     * @param roomNumber the room number to retrieve information for.
     * @param username   the username of the admin requesting the information (retrieved from cookies).
     * @return a {@link ResponseEntity} containing the serialized room information or an error message.
     * @throws NoSuchAlgorithmException if an error occurs during the process.
     * @throws JsonProcessingException  if an error occurs during JSON serialization.
     */
    @GetMapping("/getRoom")
    public ResponseEntity<String> getRoom(
            @RequestParam("roomNumber") String roomNumber,
            @CookieValue("username") String username) throws NoSuchAlgorithmException, JsonProcessingException {
        Optional<Room> roomOptional = adminPortalService.getRoomInfo(roomNumber, username);
        if (roomOptional.isPresent()) {
            String serializedRoom = roomSerializeModule.objectToJSON(roomOptional.get());
            serializedRoom = serializedRoom.substring(0, serializedRoom.length() - 1);
            serializedRoom += ", \"isBookedCurrently\": " + roomService.isRoomBooked(roomOptional.get(), LocalDate.now()) + "}";
            return ResponseEntity.ok(serializedRoom);
        }
        return new ResponseEntity<>("An error occurred.", HttpStatus.CONFLICT);
    }

    /**
     * Updates the status of a room.
     *
     * @param room     the room object with updated information.
     * @param username the username of the admin making the change (retrieved from cookies).
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     * @throws NoSuchAlgorithmException if an error occurs during the update process.
     * @throws JsonProcessingException  if an error occurs during JSON serialization.
     */
    @PostMapping("/setRoom")
    public ResponseEntity<String> setRoom(
            @RequestBody Room room,
            @CookieValue("username") String username) throws NoSuchAlgorithmException, JsonProcessingException {
        Room roomChanged = adminPortalService.setRoomStatus(room, username);
        if (roomChanged != null) {
            return ResponseEntity.ok("Successfully changed room");
        } else {
            return new ResponseEntity<>("An error occurred.", HttpStatus.CONFLICT);
        }
    }
}
