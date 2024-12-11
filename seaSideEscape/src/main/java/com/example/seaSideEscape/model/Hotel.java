package com.example.seaSideEscape.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a hotel in the SeaSide Escape application.
 * The Hotel class models basic information about a hotel, including its name, address, and rating.
 */
@Entity
public class Hotel {

    /**
     * The unique identifier for the hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the hotel.
     */
    private String name;

    /**
     * The physical address of the hotel.
     */
    private String address;

    /**
     * The rating of the hotel, typically on a scale (e.g., 1 to 5 stars).
     */
    private int rating;

    /**
     * Default constructor for the Hotel class.
     * Initializes a new instance of the Hotel class with default values.
     */
    public Hotel() {
    }

    /**
     * Gets the unique identifier for the hotel.
     *
     * @return the hotel ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the hotel.
     *
     * @param id the new hotel ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the hotel.
     *
     * @return the hotel name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param name the new hotel name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the physical address of the hotel.
     *
     * @return the hotel address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address of the hotel.
     *
     * @param address the new hotel address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the rating of the hotel.
     *
     * @return the hotel rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the hotel.
     *
     * @param rating the new rating.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
