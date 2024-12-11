package com.example.seaSideEscape.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Represents an account in the SeaSide Escape application.
 * The Account class models a user with login credentials, contact information, and associated reservations.
 */
@Entity
public class Account {

    /**
     * The unique identifier for the account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the account holder.
     */
    private String username;

    /**
     * The password for the account, stored in a hashed or encrypted format.
     */
    private String password;

    /**
     * The salt value used for password hashing.
     */
    private String salt;

    /**
     * The email address of the account holder.
     */
    private String email;

    /**
     * Enumeration representing the permission levels for the account.
     * Possible values are Guest, Clerk, and Admin.
     */
    public enum PermissionLevel {Guest, Clerk, Admin}

    /**
     * The permission level assigned to the account.
     */
    private PermissionLevel permissionLevel;

    /**
     * A reference to a single unbooked reservation associated with the account.
     * This is a bidirectional relationship managed by {@code Reservation}.
     */
    @ManyToOne
    @JsonBackReference(value = "unbookedReservations")
    private Reservation unbookedReservation;

    /**
     * A list of reservations associated with the account.
     * This is a bidirectional relationship managed by {@code Reservation}.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "reservations")
    private List<Reservation> reservations;

    /**
     * Gets the permission level of the account.
     *
     * @return the current permission level.
     */
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Sets the permission level for the account.
     *
     * @param permissionLevel the new permission level.
     */
    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    /**
     * Gets the unbooked reservation associated with the account.
     *
     * @return the unbooked reservation.
     */
    public Reservation getUnbookedReservation() {
        return unbookedReservation;
    }

    /**
     * Sets the unbooked reservation for the account.
     *
     * @param unbookedReservation the new unbooked reservation.
     */
    public void setUnbookedReservation(Reservation unbookedReservation) {
        this.unbookedReservation = unbookedReservation;
    }

    /**
     * Gets the list of reservations associated with the account.
     *
     * @return the list of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of reservations associated with the account.
     *
     * @param reservations the new list of reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the list of reservations associated with the account.
     *
     * @param reservation the reservation to be added.
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * Gets the email address of the account holder.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the account holder.
     *
     * @param email the new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the salt value used for password hashing.
     *
     * @return the salt value.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets the salt value used for password hashing.
     *
     * @param salt the new salt value.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Gets the username of the account holder.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the account holder.
     *
     * @param username the new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the account, stored in a hashed or encrypted format.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account, which should be hashed or encrypted.
     *
     * @param password the new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the unique identifier for the account.
     *
     * @return the account ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the account.
     *
     * @param id the new account ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
