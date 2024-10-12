package com.example.seaSideEscape;

import com.example.seaSideEscape.model.Bill;
import com.example.seaSideEscape.model.Charge;
import com.example.seaSideEscape.model.Reservation;
import com.example.seaSideEscape.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillingServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private BillingService billingService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock reservation
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRoomRate(new BigDecimal("100.00"));
        reservation.setTotalAmount(new BigDecimal("100.00"));

        List<Charge> charges = new ArrayList<>();
        Charge charge = new Charge();
        charge.setAmount(new BigDecimal("20.00"));
        charges.add(charge);

        reservation.setCharges(charges);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
    }

    @Test
    void generateBillTest() {
        Bill bill = billingService.generateBill(1L);

        assertNotNull(bill);
        assertEquals(new BigDecimal("100.00"), bill.getRoomRate());
        assertEquals(new BigDecimal("120.00"), bill.getTotalAmount()); // Room rate + charge
        assertEquals(new BigDecimal("12.00"), bill.getTaxes()); // 10% tax on total amount
        assertEquals(new BigDecimal("132.00"), bill.getFinalAmount()); // Total + Taxes
    }
}
