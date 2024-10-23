package com.example.seaSideEscape.service;

import com.example.seaSideEscape.model.Bill;
import com.example.seaSideEscape.model.Charge;
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
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        System.out.println("Generating bill for reservation: " + reservationId);

        List<Charge> charges = reservation.getCharges();
        BigDecimal roomRate = reservation.getRoomRate();
        BigDecimal subTotal = calculateTotalAmount(charges, roomRate);

        BigDecimal taxes = calculateTaxes(subTotal);
        BigDecimal discounts = reservation.getDiscount() != null ? reservation.getDiscount() : BigDecimal.ZERO; // Handle null discounts
        BigDecimal totalAmount = subTotal.add(taxes).subtract(discounts); // No discounts here

        // Create the Bill object
        Bill bill = new Bill();
        bill.setReservation(reservation);
        bill.setRoomRate(reservation.getRoomRate());
        bill.setCharges(charges);
        bill.setTaxes(taxes);
        bill.setSubTotal(subTotal);
        bill.setTotalAmount(totalAmount);

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
}
