package com.example.seaSideEscape.controller;

import com.example.seaSideEscape.dto.BookingPaymentRequest;
import com.example.seaSideEscape.dto.PaymentResponse;
import com.example.seaSideEscape.model.Payment;
import com.example.seaSideEscape.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payRoom")
    public ResponseEntity<Payment> payRoomBill(@RequestBody BookingPaymentRequest paymentRequest) {
        Payment payment = paymentService.processRoomPayment(
                paymentRequest.getReservationId(),
                paymentRequest.getPaymentMethod(),
                paymentRequest.getBillingAddress(),
                paymentRequest.getAmount(),
                paymentRequest.getCardNumber(),
                paymentRequest.getExpirationDate(),
                paymentRequest.getCvv()
        );
        return ResponseEntity.ok(payment);
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
