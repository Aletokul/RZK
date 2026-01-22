package com.example.paymentservice.service;

import com.example.paymentservice.client.ReservationClient;
import com.example.paymentservice.dto.ReservationDto;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationClient reservationClient;

    public PaymentService(PaymentRepository paymentRepository,
                          ReservationClient reservationClient) {
        this.paymentRepository = paymentRepository;
        this.reservationClient = reservationClient;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment createPayment(Long reservationId) {

        //Uzmi rezervaciju
        ReservationDto reservation = reservationClient.getById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        BigDecimal amount = reservation.getTotalPrice();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Reservation has no valid price");
        }

        //Napravi payment kao INITIATED
        Payment p = new Payment();
        p.setReservationId(reservationId);
        p.setAmount(amount);
        p.setStatus(PaymentStatus.INITIATED);
        p = paymentRepository.save(p);

        //Simulacija uspešnog plaćanja
        p.setStatus(PaymentStatus.SUCCESS);
        p.setPaidAt(LocalDateTime.now());
        p = paymentRepository.save(p);

        //Menja status rezervacije
        reservationClient.confirmReservation(reservationId);

        return p;
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    public List<Payment> getByReservation(Long reservationId) {
        return paymentRepository.findByReservationId(reservationId);
    }
}
