package com.example.seaSideEscape.model;

import java.math.BigDecimal;

public class ChargeAdjustment {
    private Long chargeId;
    private BigDecimal newAmount;
    private boolean approved;

    // Constructor
    public ChargeAdjustment(Long chargeId, BigDecimal newAmount, boolean approved) {
        this.chargeId = chargeId;
        this.newAmount = newAmount;
        this.approved = approved;
    }

    // Getters and setters
    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public BigDecimal getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
