package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Handles user account-related operations in the SeaSide Escape application.
 * This includes login, logout, account creation, and cart retrieval.
 */
@RestController
public class AccountController {

    private final AccountService accountService;
    private final RoomService roomService;

    private final SerializeModule<Room> serializeRoom = new SerializeModule<>();
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * Constructor for the AccountController.
     *
     * @param accountService the service handling account operations.
     * @param roomService    the service handling room operations.
     */
    @Autowired
    public AccountController(AccountService accountService, RoomService roomService) {
        this.accountService = accountService;
        this.roomService = roomService;
    }

    /**
     * Returns the login page.
     *
     * @return the name of the login page view.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Handles user login by validating credentials and setting cookies.
     *
     * @param response the HTTP response for adding cookies.
     * @param account  the account credentials provided by the user.
     * @return a {@link ResponseEntity} indicating the login status.
     * @throws Exception if an error occurs during login processing.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response, @RequestBody Account account) throws Exception {
        Optional<Account> acc = accountService.canLogin(account);
        if (acc.isPresent()) {
            Cookie username = new Cookie("username", account.getUsername());
            Cookie password = new Cookie("password", acc.get().getPassword());
            Cookie permissionLevel = new Cookie("permissionLevel", acc.get().toString());

            username.setPath("/");
            password.setPath("/");
            permissionLevel.setPath("/");
            username.setMaxAge(24 * 60 * 60);
            password.setMaxAge(24 * 60 * 60);
            permissionLevel.setMaxAge(24 * 60 * 60);
            response.addCookie(username);
            response.addCookie(password);
            response.addCookie(permissionLevel);
        } else {
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Successfully logged into account.", HttpStatus.OK);
    }

    /**
     * Handles user logout by clearing cookies.
     *
     * @param response the HTTP response for clearing cookies.
     * @return a confirmation message.
     * @throws Exception if an error occurs during logout processing.
     */
    @PostMapping("/logoutAccount")
    public String logout(HttpServletResponse response) throws Exception {
        Cookie username = new Cookie("username", null);
        Cookie password = new Cookie("password", null);
        Cookie permissionLevel = new Cookie("permissionLevel", null);

        username.setPath("/");
        password.setPath("/");
        permissionLevel.setPath("/");
        username.setMaxAge(0);
        password.setMaxAge(0);
        permissionLevel.setMaxAge(0);

        response.addCookie(username);
        response.addCookie(password);
        response.addCookie(permissionLevel);

        return "done";
    }

    /**
     * Returns the admin login page.
     *
     * @return the name of the admin login page view.
     */
    @GetMapping("/adminLogin")
    public String adminLogin() {
        return "adminLogin";
    }

    /**
     * Creates a new account with a default Guest permission level.
     *
     * @param account the account details provided by the user.
     * @return a {@link ResponseEntity} indicating the account creation status.
     * @throws NoSuchAlgorithmException if an error occurs during account creation.
     */
    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) throws NoSuchAlgorithmException {
        account.setPermissionLevel(Account.PermissionLevel.Guest);
        return accountService.createAccount(account);
    }

    /**
     * Retrieves the current user's cart containing reserved rooms.
     *
     * @param username the username obtained from the cookies.
     * @return a {@link ResponseEntity} containing the cart in JSON format.
     * @throws JsonProcessingException if an error occurs during JSON serialization.
     */
    @GetMapping("/getCart")
    public ResponseEntity<String> getCart(@CookieValue("username") String username) throws JsonProcessingException {
        return ResponseEntity.ok(serializeRoom.listToJSON(roomService.getRoomsInCart(username)));
    }
}
