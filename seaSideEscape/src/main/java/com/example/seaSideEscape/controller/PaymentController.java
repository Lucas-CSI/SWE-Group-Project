package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.service.PaymentService;
import com.example.seaSideEscape.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public Payment payBill(@RequestParam Long id,
                           @RequestParam String paymentMethod,
                           @RequestParam String billingAddress,
                           @RequestParam BigDecimal amount,
                           @RequestParam boolean isRoomPayment) {
        return paymentService.processPayment(id, paymentMethod, billingAddress, amount, isRoomPayment);
    }
}
