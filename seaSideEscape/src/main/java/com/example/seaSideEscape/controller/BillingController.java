package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.BillingService;
import com.example.seaSideEscape.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Bill getPaymentInformation(@PathVariable Long reservationId) {
        return billingService.generateBill(reservationId);
    }
}