package com.example.seaSideEscape.model;

import jakarta.persistence.*;

/**
 * Represents a room in the SeaSide Escape application.
 * The Room class models the characteristics of a room, including its features, rates, and attributes.
 */
@Entity
public class Room {

    /**
     * The unique identifier for the room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique room number assigned to the room.
     */
    private String roomNumber;

    /**
     * The theme of the room, representing its style and decor.
     */
    private Themes theme;

    /**
     * The quality level of the room, indicating its amenities and service level.
     * Possible values are Economy, Comfort, Business, and Executive.
     */
    private QualityLevel qualityLevel;

    /**
     * The type of bed in the room (e.g., twin, full, queen, king).
     */
    private String bedType;

    /**
     * Indicates whether smoking is allowed in the room.
     */
    private boolean isSmokingAllowed;

    /**
     * The maximum nightly rate for the room.
     */
    private double maxRate;

    /**
     * Indicates whether the room has an ocean view.
     */
    private boolean oceanView;

    /**
     * Enumeration representing the possible themes of a room.
     * Includes NATURE_RETREAT, URBAN_ELEGANCE, and VINTAGE_CHARM.
     */
    public enum Themes {NATURE_RETREAT, URBAN_ELEGANCE, VINTAGE_CHARM}

    /**
     * Enumeration representing the quality levels of a room.
     * Includes Economy, Comfort, Business, and Executive.
     */
    public enum QualityLevel {Economy, Comfort, Business, Executive}

    /**
     * Gets the unique identifier for the room.
     *
     * @return the room ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the room.
     *
     * @param id the new room ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the room number.
     *
     * @return the room number.
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber the new room number.
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the theme of the room.
     *
     * @return the theme of the room.
     */
    public Themes getTheme() {
        return theme;
    }

    /**
     * Sets the theme of the room.
     *
     * @param theme the new theme.
     */
    public void setTheme(Themes theme) {
        this.theme = theme;
    }

    /**
     * Gets the quality level of the room.
     *
     * @return the quality level of the room.
     */
    public QualityLevel getQualityLevel() {
        return qualityLevel;
    }

    /**
     * Sets the quality level of the room.
     *
     * @param qualityLevel the new quality level.
     */
    public void setQualityLevel(QualityLevel qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    /**
     * Gets the type of bed in the room.
     *
     * @return the bed type.
     */
    public String getBedType() {
        return bedType;
    }

    /**
     * Sets the type of bed in the room.
     *
     * @param bedType the new bed type.
     */
    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    /**
     * Checks if smoking is allowed in the room.
     *
     * @return {@code true} if smoking is allowed; {@code false} otherwise.
     */
    public Boolean isSmokingAllowed() {
        return isSmokingAllowed;
    }

    /**
     * Sets whether smoking is allowed in the room.
     *
     * @param smokingAllowed {@code true} to allow smoking; {@code false} to prohibit smoking.
     */
    public void setSmokingAllowed(Boolean smokingAllowed) {
        this.isSmokingAllowed = smokingAllowed;
    }

    /**
     * Gets the maximum nightly rate for the room.
     *
     * @return the maximum rate.
     */
    public double getMaxRate() {
        return maxRate;
    }

    /**
     * Sets the maximum nightly rate for the room.
     *
     * @param maxRate the new maximum rate.
     */
    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

    /**
     * Checks if the room has an ocean view.
     *
     * @return {@code true} if the room has an ocean view; {@code false} otherwise.
     */
    public boolean isOceanView() {
        return oceanView;
    }

    /**
     * Sets whether the room has an ocean view.
     *
     * @param oceanView {@code true} if the room has an ocean view; {@code false} otherwise.
     */
    public void setOceanView(boolean oceanView) {
        this.oceanView = oceanView;
    }
}
