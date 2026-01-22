package com.example.reservationservice.feign;

import com.example.reservationservice.dto.LotDto;
import com.example.reservationservice.dto.SpotDto;
import com.example.reservationservice.dto.ZonePricingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "parking-lot-service")
public interface ReservationProxy {

    @GetMapping("/spots/{id}")
    SpotDto getSpotById(@PathVariable("id") Long id);

    @GetMapping("/lots/{id}")
    LotDto getLotById(@PathVariable("id") Long id);

    @GetMapping("/zone-pricing/{zone}")
    ZonePricingDto getZonePricingByZone(@PathVariable("zone") String zone);

}
