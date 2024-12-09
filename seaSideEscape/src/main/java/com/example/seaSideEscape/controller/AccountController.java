package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.SerializeModule;
import com.example.seaSideEscape.model.Booking;
import com.example.seaSideEscape.model.Room;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.service.BookingService;
import com.example.seaSideEscape.service.ReservationService;
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

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

// Change to RequestMapping????
@RestController
public class AccountController {
    private final AccountService accountService;
    private final RoomService roomService;
    private final SerializeModule<Room> serializeRoom = new SerializeModule<>();
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService, RoomService roomService) {
        this.accountService = accountService;
        this.roomService = roomService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response, @RequestBody Account account) throws Exception {
        Optional<Account> acc = accountService.canLogin(account);
        if(acc.isPresent()){
            Cookie username = new Cookie("username", account.getUsername());
            Cookie password = new Cookie("password", acc.get().getPassword());

            username.setPath("/");
            password.setPath("/");
            username.setMaxAge(24 * 60 * 60);
            password.setMaxAge(24 * 60 * 60);
            response.addCookie(username);
            response.addCookie(password);
        }else{
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Successfully logged into account.", HttpStatus.OK);
    }

    @PostMapping("/logoutAccount")
    public String logout(HttpServletResponse response) throws Exception {
        Cookie username = new Cookie("username", null);
        Cookie password = new Cookie("password", null);
        username.setPath("/");
        password.setPath("/");
        username.setMaxAge(0);
        password.setMaxAge(0);
        response.addCookie(username);
        response.addCookie(password);
        return "done";
    }
    // TODO: Get user's reservations
    //@RequestMapping(value = "/profile/reservations", method = GET)
    //@ResponseBody

    @GetMapping("/adminLogin")
    public String adminLogin(){return "adminLogin";}

    /*
    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(HttpServletResponse response, @RequestBody Account account) throws NoSuchAlgorithmException {
        Optional<Account> acc = accountService.canLogin(account);
        if(acc.isPresent()){
            if (acc.get().isAdmin()){
                response.addCookie(new Cookie("admin", account.getUsername()));
                response.addCookie(new Cookie("password", acc.get().getPassword()));

                //return ResponseEntity.status(HttpStatus.FOUND)
                //.location(URI.create("/admin/homepage")).build();
                return ResponseEntity.ok("Admin login successful");

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
    */

    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(HttpServletResponse response, @RequestBody Account account) throws NoSuchAlgorithmException {
        System.out.println("Attempting to log in with username: " + account.getUsername());

        // Attempt to find the account in the database
        Optional<Account> acc = accountService.canLogin(account);

        if (acc.isPresent()) {
            System.out.println("Account found for username: " + account.getUsername());

            // Check if the found account has admin privileges
            if (acc.get().isAdmin()) {
                System.out.println("Account is admin. Setting cookies...");

                response.addCookie(new Cookie("admin", account.getUsername()));
                response.addCookie(new Cookie("password", acc.get().getPassword()));

                System.out.println("Admin login successful for username: " + account.getUsername());
                return ResponseEntity.ok("Admin login successful");
            } else {
                System.out.println("Account is not an admin. Unauthorized access attempt.");
            }
        } else {
            System.out.println("No account found for username: " + account.getUsername());
        }

        System.out.println("Returning 401 Unauthorized for username: " + account.getUsername());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }


    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) throws NoSuchAlgorithmException {
        return accountService.createAccount(account);
    }

    @GetMapping("/getCart")
    public ResponseEntity<String> getCart(@CookieValue("username") String username) throws NoSuchAlgorithmException, JsonProcessingException {
        return ResponseEntity.ok(serializeRoom.listToJSON(roomService.getRoomsInCart(username)));
    }
}
