package com.example.seaSideEscape.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a bill associated with a reservation in the SeaSide Escape application.
 * The Bill class models the financial details including room rates, charges, taxes, and total amounts.
 */
@Entity
@JsonPropertyOrder({ "reservation", "roomRate", "charges", "taxes", "totalAmount", "finalAmount" })
public class Bill {

    /**
     * The unique identifier for the bill.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The reservation associated with this bill.
     * This is a many-to-one relationship as multiple bills can be linked to a single reservation.
     */
    @ManyToOne
    private Reservation reservation;

    /**
     * The total discounts applied to the bill.
     */
    private BigDecimal discounts;

    /**
     * The subtotal of the bill before taxes and discounts.
     */
    private BigDecimal subTotal;

    /**
     * The total amount of taxes applied to the bill.
     */
    private BigDecimal taxes;

    /**
     * The total amount of the bill, including taxes and before discounts.
     */
    private BigDecimal totalAmount;

    /**
     * The room rate associated with the reservation.
     */
    private BigDecimal roomRate;

    /**
     * The final amount to be paid, after applying discounts and taxes.
     */
    private BigDecimal finalAmount;

    /**
     * The list of additional charges applied to the bill.
     */
    @OneToMany
    private List<Charge> charges;

    /**
     * Gets the reservation associated with the bill.
     *
     * @return the reservation.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation associated with the bill.
     *
     * @param reservation the reservation to associate with this bill.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the final amount to be paid.
     *
     * @return the final amount.
     */
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    /**
     * Sets the final amount to be paid.
     *
     * @param finalAmount the new final amount.
     */
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    /**
     * Gets the total discounts applied to the bill.
     *
     * @return the discounts.
     */
    public BigDecimal getDiscounts() {
        return discounts;
    }

    /**
     * Sets the total discounts applied to the bill.
     *
     * @param discounts the new discounts.
     */
    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }

    /**
     * Gets the total amount of the bill.
     *
     * @return the total amount.
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the bill.
     *
     * @param totalAmount the new total amount.
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the subtotal of the bill before taxes and discounts.
     *
     * @return the subtotal.
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the subtotal of the bill before taxes and discounts.
     *
     * @param subTotal the new subtotal.
     */
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Gets the unique identifier for the bill.
     *
     * @return the bill ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the bill.
     *
     * @param id the new bill ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the list of additional charges applied to the bill.
     *
     * @return the list of charges.
     */
    public List<Charge> getCharges() {
        return charges;
    }

    /**
     * Sets the list of additional charges applied to the bill.
     *
     * @param charges the new list of charges.
     */
    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    /**
     * Gets the total taxes applied to the bill.
     *
     * @return the taxes.
     */
    public BigDecimal getTaxes() {
        return taxes;
    }

    /**
     * Sets the total taxes applied to the bill.
     *
     * @param taxes the new taxes.
     */
    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    /**
     * Gets the room rate associated with the reservation.
     *
     * @return the room rate.
     */
    public BigDecimal getRoomRate() {
        return roomRate;
    }

    /**
     * Sets the room rate associated with the reservation.
     *
     * @param roomRate the new room rate.
     */
    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }
}
