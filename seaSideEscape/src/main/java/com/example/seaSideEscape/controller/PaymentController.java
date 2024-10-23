package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.PaymentService;
import com.example.seaSideEscape.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Payment payBill(@RequestParam Long reservationId,
                           @RequestParam String paymentMethod,
                           @RequestParam String billingAddress,
                           @RequestParam BigDecimal amount) {
        return paymentService.processPayment(reservationId, paymentMethod, billingAddress, amount);
    }
}
