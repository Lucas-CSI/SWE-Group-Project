package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Account;
import com.example.seaSideEscape.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Service class for managing administrative operations in the SeaSideEscape application.
 * Provides methods for creating accounts, modifying permissions, and managing room data.
 */
@Service
public class AdminPortalService {

    private final AccountService accountService;
    private final EmailService emailService;
    private final RoomService roomService;

    /**
     * Constructs a new AdminPortalService with the specified dependencies.
     *
     * @param accountService the service for managing accounts
     * @param emailService   the service for sending emails
     * @param roomService    the service for managing room data
     */
    public AdminPortalService(AccountService accountService, EmailService emailService, RoomService roomService) {
        this.accountService = accountService;
        this.emailService = emailService;
        this.roomService = roomService;
    }

    /**
     * Creates a new account with the specified details and permission level.
     *
     * @param username        the username of the new account
     * @param email           the email of the new account
     * @param permissionLevel the permission level of the new account
     * @return the newly created account
     */
    private Account makeAccount(String username, String email, Account.PermissionLevel permissionLevel) {
        Account toCreate = new Account();
        String password = accountService.createSalt();
        toCreate.setUsername(username);
        toCreate.setEmail(email);
        toCreate.setPassword(password);
        toCreate.setPermissionLevel(permissionLevel);
        return toCreate;
    }

    /**
     * Checks if a user has the required permission level.
     *
     * @param username         the username of the account to check
     * @param permissionLevel  the required permission level
     * @return true if the user has the required permission level, false otherwise
     */
    private boolean checkPermission(String username, Account.PermissionLevel permissionLevel) {
        Account account = accountService.getAccountObject(username);
        return account != null && accountService.checkPermission(account, permissionLevel);
    }

    /**
     * Creates a new non-guest account if the requesting user has admin permissions.
     * Sends an email with account details upon successful creation.
     *
     * @param toCreateUsername the username of the account to be created
     * @param toCreateEmail    the email of the account to be created
     * @param username         the username of the requesting user
     * @param permissionLevel  the permission level of the new account
     * @return a ResponseEntity with the operation status
     * @throws NoSuchAlgorithmException if an error occurs while hashing the password
     */
    public ResponseEntity<String> createNonGuestAccount(String toCreateUsername, String toCreateEmail, String username, Account.PermissionLevel permissionLevel) throws NoSuchAlgorithmException {
        if (checkPermission(username, Account.PermissionLevel.Admin)) {
            Account account = makeAccount(toCreateUsername, toCreateEmail, permissionLevel);
            String password = account.getPassword();
            ResponseEntity<String> result = accountService.createAccount(account);
            if (result.getStatusCode() == HttpStatus.OK) {
                emailService.sendAccountDetails(toCreateEmail, toCreateUsername, password, permissionLevel);
            }
            return result;
        } else {
            return new ResponseEntity<>("Error: Invalid permissions", HttpStatus.CONFLICT);
        }
    }

    /**
     * Modifies the permission level of an account if the requesting user has admin permissions.
     *
     * @param toModifyUsername the username of the account to modify
     * @param permissionLevel  the new permission level to set
     * @param username         the username of the requesting user
     * @return a string indicating the operation result
     * @throws NoSuchAlgorithmException if an error occurs during the operation
     */
    @Transactional
    public String modifyPermissionLevel(String toModifyUsername, Account.PermissionLevel permissionLevel, String username) throws NoSuchAlgorithmException {
        if (checkPermission(username, Account.PermissionLevel.Admin)) {
            Account account = accountService.getAccountObject(toModifyUsername);
            if (account != null) {
                account.setPermissionLevel(permissionLevel);
                accountService.saveAccount(account);
            } else {
                return "Error: Account does not exist";
            }
        } else {
            return "Error: Not enough permission";
        }
        return "Successfully modified permission level";
    }

    /**
     * Retrieves information about a room if the requesting user has clerk or higher permissions.
     *
     * @param roomNumber the number of the room to retrieve
     * @param username   the username of the requesting user
     * @return an Optional containing the room if found, or empty if not found or insufficient permissions
     */
    public Optional<Room> getRoomInfo(String roomNumber, String username) {
        if (checkPermission(username, Account.PermissionLevel.Clerk)) {
            return roomService.getRoomInfo(roomNumber);
        }
        return Optional.empty();
    }

    /**
     * Updates the status of a room if the requesting user has admin permissions.
     *
     * @param room     the room to update
     * @param username the username of the requesting user
     * @return the updated room if the operation is successful, or null if permissions are insufficient
     */
    public Room setRoomStatus(Room room, String username) {
        if (checkPermission(username, Account.PermissionLevel.Admin)) {
            return roomService.save(room);
        }
        return null;
    }
}
