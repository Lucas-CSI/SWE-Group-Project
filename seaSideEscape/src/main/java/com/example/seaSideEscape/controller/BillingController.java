package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.BillingService;
import com.example.seaSideEscape.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/{reservationId}")
    public ResponseEntity<Bill> getPaymentInformation(@PathVariable Long reservationId) {
        try {
            Bill bill = billingService.generateBill(reservationId);
            return ResponseEntity.ok(bill);
        } catch (IllegalStateException e) {
            // Handle incomplete charges or other precondition failures
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IllegalArgumentException e) {
            // Handle reservation not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}