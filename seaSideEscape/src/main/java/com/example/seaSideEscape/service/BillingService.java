package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Bill;
import com.example.seaSideEscape.model.Charge;
import com.example.seaSideEscape.model.ChargeAdjustment;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Service class for handling billing operations.
 * Provides methods for generating bills, calculating charges and taxes,
 * and adjusting charges associated with reservations.
 */
@Service
public class BillingService {

    private final ReservationRepository reservationRepository;

    /**
     * Constructs a new BillingService with the specified ReservationRepository.
     *
     * @param reservationRepository the repository for managing reservations
     */
    public BillingService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Generates a bill for the specified reservation.
     * Ensures all charges are finalized before calculating the bill.
     *
     * @param reservationId the ID of the reservation for which the bill is generated
     * @return the generated Bill object
     * @throws IllegalArgumentException if the reservation is not found
     * @throws IllegalStateException    if not all charges are finalized
     */
    public Bill generateBill(Long reservationId) {
        // Retrieve and validate reservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        System.out.println("Generating bill for reservation: " + reservation.getId());

        List<Charge> charges = reservation.getCharges();
        if (charges.stream().anyMatch(charge -> !charge.isFinalized())) {
            throw new IllegalStateException("Not all charges are finalized for this reservation.");
        }

        BigDecimal roomRate = reservation.getRoomRate();
        BigDecimal subTotal = calculateTotalAmount(charges, roomRate);
        BigDecimal taxes = calculateTaxes(subTotal);
        BigDecimal discounts = reservation.getDiscount() != null ? reservation.getDiscount() : BigDecimal.ZERO;
        BigDecimal finalAmount = subTotal.add(taxes).subtract(discounts);

        Bill bill = new Bill();
        bill.setReservation(reservation);
        bill.setRoomRate(roomRate);
        bill.setCharges(charges);
        bill.setSubTotal(subTotal);
        bill.setTaxes(taxes);
        bill.setDiscounts(discounts);
        bill.setTotalAmount(finalAmount);

        return bill;
    }

    /**
     * Calculates the total amount for a reservation, including charges and room rate.
     *
     * @param charges  the list of charges associated with the reservation
     * @param roomRate the room rate for the reservation
     * @return the total amount as a BigDecimal
     */
    private BigDecimal calculateTotalAmount(List<Charge> charges, BigDecimal roomRate) {
        BigDecimal total = roomRate;
        for (Charge charge : charges) {
            total = total.add(charge.getAmount());
        }
        return total;
    }

    /**
     * Calculates taxes based on a predefined tax rate (10%).
     *
     * @param amount the taxable amount
     * @return the calculated taxes as a BigDecimal, rounded to 2 decimal places
     */
    private BigDecimal calculateTaxes(BigDecimal amount) {
        BigDecimal taxRate = new BigDecimal("0.10"); // 10% tax rate
        return amount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);  // Round taxes to 2 decimal places
    }

    /**
     * Adjusts charges for a reservation based on the specified adjustments.
     * Only approved adjustments are applied.
     *
     * @param reservationId the ID of the reservation to adjust charges for
     * @param adjustments   the list of ChargeAdjustment objects
     * @return the updated Bill after adjustments
     * @throws IllegalArgumentException if the reservation or a charge is not found
     */
    public Bill adjustCharges(Long reservationId, List<ChargeAdjustment> adjustments) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        for (ChargeAdjustment adjustment : adjustments) {
            Charge charge = reservation.getCharges().stream()
                    .filter(c -> c.getId().equals(adjustment.getChargeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Charge not found"));

            if (adjustment.isApproved()) {
                charge.setAmount(adjustment.getNewAmount());
            }
        }

        return generateBill(reservationId);
    }
}
