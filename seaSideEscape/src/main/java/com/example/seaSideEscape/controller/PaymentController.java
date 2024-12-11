package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.dto.PaymentResponse;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.service.AccountService;
import com.example.seaSideEscape.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.seaSideEscape.model.Account;

/**
 * Handles payment-related operations in the SeaSide Escape application.
 * The PaymentController provides endpoints for processing payments for room reservations and event bookings.
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final AccountService accountService;

    /**
     * Constructor for the PaymentController.
     *
     * @param paymentService the service handling payment processing.
     * @param accountService the service handling account operations.
     */
    @Autowired
    public PaymentController(PaymentService paymentService, AccountService accountService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    /**
     * Processes a payment for a room bill associated with the logged-in user's account.
     *
     * @param paymentRequest the payment request details including payment method, billing address, and card information.
     * @param username       the username of the account making the payment, retrieved from cookies.
     * @return a {@link ResponseEntity} containing a success message with a confirmation ID or an error message.
     */
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

    /**
     * Processes payment for an event booking and books the event simultaneously.
     *
     * @param bookingPaymentRequest the payment and booking details including event booking ID, payment method,
     *                              billing address, and card information.
     * @return a {@link ResponseEntity} containing a {@link PaymentResponse} with payment and event booking details.
     */
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
