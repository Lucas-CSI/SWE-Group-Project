package com.example.seaSideEscape.model;

import java.math.BigDecimal;

/**
 * Represents an adjustment to a charge in the SeaSide Escape application.
 * The ChargeAdjustment class models changes to a charge's amount, including its approval status.
 */
public class ChargeAdjustment {

    /**
     * The unique identifier of the charge to be adjusted.
     */
    private Long chargeId;

    /**
     * The new monetary amount to be set for the charge.
     */
    private BigDecimal newAmount;

    /**
     * Indicates whether the adjustment has been approved.
     */
    private boolean approved;

    /**
     * Constructs a ChargeAdjustment with the specified charge ID, new amount, and approval status.
     *
     * @param chargeId  the unique identifier of the charge to adjust.
     * @param newAmount the new amount to set for the charge.
     * @param approved  whether the adjustment is approved.
     */
    public ChargeAdjustment(Long chargeId, BigDecimal newAmount, boolean approved) {
        this.chargeId = chargeId;
        this.newAmount = newAmount;
        this.approved = approved;
    }

    /**
     * Gets the unique identifier of the charge to be adjusted.
     *
     * @return the charge ID.
     */
    public Long getChargeId() {
        return chargeId;
    }

    /**
     * Sets the unique identifier of the charge to be adjusted.
     *
     * @param chargeId the new charge ID.
     */
    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    /**
     * Gets the new monetary amount to be set for the charge.
     *
     * @return the new amount.
     */
    public BigDecimal getNewAmount() {
        return newAmount;
    }

    /**
     * Sets the new monetary amount to be set for the charge.
     *
     * @param newAmount the new amount.
     */
    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
    }

    /**
     * Checks if the adjustment has been approved.
     *
     * @return {@code true} if the adjustment is approved; {@code false} otherwise.
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the approval status of the adjustment.
     *
     * @param approved {@code true} to approve the adjustment; {@code false} to disapprove it.
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
