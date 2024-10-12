package com.example.seaSideEscape;

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

        System.out.println("Generating bill for reservation: " + reservation.getId());

        List<Charge> charges = reservation.getCharges();
        BigDecimal roomRate = reservation.getRoomRate();
        BigDecimal totalAmount = calculateTotalAmount(charges, roomRate);

        BigDecimal taxes = calculateTaxes(totalAmount);
        BigDecimal finalAmount = totalAmount.add(taxes); // No discounts here

        // Create the Bill object
        Bill bill = new Bill();
        bill.setReservation(reservation);
        bill.setRoomRate(roomRate);
        bill.setCharges(charges);
        bill.setTaxes(taxes);
        bill.setTotalAmount(totalAmount);
        bill.setFinalAmount(finalAmount);

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
