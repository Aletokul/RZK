package com.example.paymentservice.controller;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAllPayments();
    }

    // POST /payments/charge?reservationId=1
    @PostMapping("/charge")
    public Payment charge(@RequestParam Long reservationId) {
        return paymentService.createPayment(reservationId);
    }

    // GET /payments/{id}
    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    // GET /payments/reservation/1
    @GetMapping("/reservation/{reservationId}")
    public List<Payment> byReservation(@PathVariable Long reservationId) {
        return paymentService.getByReservation(reservationId);
    }
}
