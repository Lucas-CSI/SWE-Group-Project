package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.dto.PaymentResponse;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.repository.ReservationRepository;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.EmailService;
import com.example.seaSideEscape.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.seaSideEscape.model.Account;



import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {


    private final PaymentService paymentService;
    private final AccountService accountService;

    @Autowired
    public PaymentController(PaymentService paymentService, AccountService accountService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @PostMapping("/payRoom")
    public ResponseEntity<String> payRoomBill(@RequestBody BookingPaymentRequest paymentRequest,
                                              @CookieValue("username") String username) {

        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body("Username is missing in the request.");
        }

        try {
            Account account = accountService.findAccountByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));


            Payment payment = paymentService.processRoomPayment(
                    paymentRequest.getPaymentMethod(),
                    paymentRequest.getBillingAddress(),
                    paymentRequest.getCardNumber(),
                    paymentRequest.getExpirationDate(),
                    paymentRequest.getCvv(),
                    account
            );

            return ResponseEntity.ok("Payment successful! Your confirmation ID is " + payment.getID());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing payment: " + e.getMessage());
        }
    }


    @PostMapping("/payEvent")
    public ResponseEntity<PaymentResponse> bookAndPayEvent(@RequestBody BookingPaymentRequest bookingPaymentRequest) {
        System.out.println("Received BookingPaymentRequest: " + bookingPaymentRequest);

        if (bookingPaymentRequest.getEventBookingId() == null) {
            throw new IllegalArgumentException("EventBooking ID is missing in the request.");
        }
        Payment payment = paymentService.bookAndPayEvent(bookingPaymentRequest);
        Long eventBookingId = payment.getEventBooking().getId();
        PaymentResponse response = new PaymentResponse(payment, eventBookingId);
        return ResponseEntity.ok(response);
    }
}
