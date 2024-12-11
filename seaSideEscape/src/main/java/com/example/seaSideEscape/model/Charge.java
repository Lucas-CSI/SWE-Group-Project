package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a charge associated with a reservation in the SeaSide Escape application.
 * The Charge class models an individual financial item, including its description, amount, and status.
 */
@Entity
public class Charge {

    /**
     * The unique identifier for the charge.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A brief description of the charge.
     */
    private String description;

    /**
     * The monetary amount of the charge.
     */
    private BigDecimal amount;

    /**
     * Indicates whether the charge has been finalized.
     * A finalized charge cannot be modified.
     */
    private boolean finalized;

    /**
     * The reservation associated with this charge.
     * This is a many-to-one relationship, as multiple charges can be linked to a single reservation.
     */
    @ManyToOne
    private Reservation reservation;

    /**
     * Gets the unique identifier for the charge.
     *
     * @return the charge ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the charge.
     *
     * @param id the new charge ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the description of the charge.
     *
     * @return the description of the charge.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the charge.
     *
     * @param description the new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the monetary amount of the charge.
     *
     * @return the amount of the charge.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the monetary amount of the charge.
     *
     * @param amount the new amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the finalized status of the charge.
     *
     * @return {@code true} if the charge is finalized; {@code false} otherwise.
     */
    public boolean isFinalized() {
        return finalized;
    }

    /**
     * Sets the finalized status of the charge.
     *
     * @param finalized the new finalized status.
     */
    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    /**
     * Gets the reservation associated with the charge.
     *
     * @return the reservation.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation associated with the charge.
     *
     * @param reservation the new reservation.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
