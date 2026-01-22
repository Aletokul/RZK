package com.example.paymentservice.client;

import com.example.paymentservice.dto.ReservationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "reservation-service")
public interface ReservationClient {

    @GetMapping("/reservations/{id}")
    ReservationDto getById(@PathVariable("id") Long id);

    @PostMapping("/reservations/{id}/confirm")
    void confirmReservation(@PathVariable("id") Long id);
}
