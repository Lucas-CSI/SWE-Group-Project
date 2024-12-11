package com.example.seaSideEscape.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

/**
 * Represents a venue available for events in the SeaSide Escape application.
 * The Venue class models details such as location, capacity, rates, and booking status.
 */
@Entity
public class Venue {

    /**
     * The unique identifier for the venue.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the venue.
     */
    private String name;

    /**
     * The location of the venue.
     */
    private String location;

    /**
     * The maximum capacity of the venue, representing the number of guests it can accommodate.
     */
    private int capacity;

    /**
     * Indicates whether the venue is currently booked.
     */
    private boolean isBooked;

    /**
     * The floor number where the venue is located.
     */
    private int floorNumber;

    /**
     * The base rental rate for the venue.
     */
    private BigDecimal baseRate;

    /**
     * Any additional charges that may apply to the venue's rental.
     */
    private BigDecimal additionalCharges;

    /**
     * Indicates whether the payment for the venue has been made.
     */
    private boolean isPaid;

    /**
     * Gets the unique identifier for the venue.
     *
     * @return the venue ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the venue.
     *
     * @param id the new venue ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the venue.
     *
     * @return the venue name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the venue.
     *
     * @param name the new venue name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the venue.
     *
     * @return the venue location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the venue.
     *
     * @param location the new venue location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the maximum capacity of the venue.
     *
     * @return the venue capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the maximum capacity of the venue.
     *
     * @param capacity the new venue capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Checks if the venue is currently booked.
     *
     * @return {@code true} if the venue is booked; {@code false} otherwise.
     */
    public boolean isBooked() {
        return isBooked;
    }

    /**
     * Sets the booking status of the venue.
     *
     * @param booked {@code true} to mark the venue as booked; {@code false} otherwise.
     */
    public void setBooked(boolean booked) {
        this.isBooked = booked;
    }

    /**
     * Gets the floor number where the venue is located.
     *
     * @return the floor number.
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Sets the floor number where the venue is located.
     *
     * @param floorNumber the new floor number.
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * Gets the base rental rate for the venue.
     *
     * @return the base rental rate.
     */
    public BigDecimal getBaseRate() {
        return baseRate;
    }

    /**
     * Sets the base rental rate for the venue.
     *
     * @param baseRate the new base rental rate.
     */
    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    /**
     * Gets any additional charges that may apply to the venue's rental.
     *
     * @return the additional charges.
     */
    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    /**
     * Sets additional charges that may apply to the venue's rental.
     *
     * @param additionalCharges the new additional charges.
     */
    public void setAdditionalCharges(BigDecimal additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    /**
     * Checks if the payment for the venue has been made.
     *
     * @return {@code true} if the payment has been made; {@code false} otherwise.
     */
    private boolean getIsPaid() {
        return isPaid;
    }

    /**
     * Sets the payment status for the venue.
     *
     * @param isPaid {@code true} if the payment has been made; {@code false} otherwise.
     */
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
