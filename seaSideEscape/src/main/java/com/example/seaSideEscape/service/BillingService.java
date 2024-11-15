package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Bill;
import com.example.seaSideEscape.model.Charge;
import com.example.seaSideEscape.model.ChargeAdjustment;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BillingService {

    @Autowired
    private ReservationRepository reservationRepository;

    public BillingService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

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


    private BigDecimal calculateTotalAmount(List<Charge> charges, BigDecimal roomRate) {
        BigDecimal total = roomRate;
        for (Charge charge : charges) {
            total = total.add(charge.getAmount());
        }
        return total;
    }

    private BigDecimal calculateTaxes(BigDecimal amount) {
        BigDecimal taxRate = new BigDecimal("0.10"); // 10% tax rate
        return amount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);  // Round taxes to 2 decimal places
    }

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
