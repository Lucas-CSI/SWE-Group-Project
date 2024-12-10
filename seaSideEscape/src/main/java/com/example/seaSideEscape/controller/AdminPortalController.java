package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.AdminPortalService;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminPortalController {
    private final AdminPortalService adminPortalService;
    private final RoomService roomService;
    private final SerializeModule<Room> roomSerializeModule = new SerializeModule<Room>();

    @Autowired
    public AdminPortalController(AdminPortalService adminPortalService, RoomService roomService) {
        this.adminPortalService = adminPortalService;
        this.roomService = roomService;
    }

    @PostMapping("/createAccount/clerk")
    public ResponseEntity<String> createClerkAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Clerk);
    }

    @PostMapping("/createAccount/admin")
    public ResponseEntity<String> createAdminAccount(@RequestParam("username") String toCreateUsername, @RequestParam("email") String toCreateEmail, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        return adminPortalService.createNonGuestAccount(toCreateUsername, toCreateEmail, username, Account.PermissionLevel.Admin);
    }

    @PostMapping("/changePermission")
    public ResponseEntity<String> changeAccountPermission(@RequestParam("username") String toChangeUsername, @RequestParam("permissionLevel") Integer permissionLevel, @CookieValue("username") String username) throws NoSuchAlgorithmException {
        String response = adminPortalService.modifyPermissionLevel(toChangeUsername, Account.PermissionLevel.values()[permissionLevel], username);

        if(!response.contains("Error")){
            return ResponseEntity.ok("Successfully changed permission");
        }else{
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getRoom")
    public ResponseEntity<String> getRoom(@RequestParam("roomNumber") String roomNumber, @CookieValue("username") String username) throws NoSuchAlgorithmException, JsonProcessingException {
        Optional<Room> roomOptional = roomService.getRoomInfo(roomNumber);
        if(roomOptional.isPresent()){
            String serializedRoom = roomSerializeModule.objectToJSON(roomOptional.get());
            serializedRoom = serializedRoom.substring(0,serializedRoom.length()-1);
            serializedRoom += ", \"isBookedCurrently\": "+ roomService.isRoomBooked(roomOptional.get(), LocalDate.now()) + "}";
            System.out.println(serializedRoom);
            return ResponseEntity.ok(serializedRoom);
        }
        return new ResponseEntity<>("Error: Room not found.", HttpStatus.CONFLICT);
    }
}
