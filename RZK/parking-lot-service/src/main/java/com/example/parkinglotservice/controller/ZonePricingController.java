package com.example.parkinglotservice.controller;

import com.example.parkinglotservice.model.ZonePricing;
import com.example.parkinglotservice.service.ZonePricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zone-pricing")
public class ZonePricingController {

    private final ZonePricingService zonePricingService;

    public ZonePricingController(ZonePricingService zonePricingService) {
        this.zonePricingService = zonePricingService;
    }

    // GET /zone-pricing
    @GetMapping
    public List<ZonePricing> getAll() {
        return zonePricingService.getAll();
    }

    // GET /zone-pricing/{zone}
    @GetMapping("/{zone}")
    public ResponseEntity<ZonePricing> getByZone(@PathVariable String zone) {
        return zonePricingService.getByZone(zone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /zone-pricing
    @PostMapping
    public ResponseEntity<ZonePricing> create(@RequestBody ZonePricing zonePricing) {
        ZonePricing saved = zonePricingService.create(zonePricing);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /zone-pricing/{zone}
    @PutMapping("/{zone}")
    public ResponseEntity<ZonePricing> update(
            @PathVariable String zone,
            @RequestBody ZonePricing updated
    ) {
        return zonePricingService.getByZone(zone)
                .map(existing -> {
                    // ključ (zone) obično NE menjamo ovde, samo ostale vrednosti
                    existing.setPricePerHour(updated.getPricePerHour());
                    existing.setRoundToMinutes(updated.getRoundToMinutes());
                    ZonePricing saved = zonePricingService.update(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /zone-pricing/{zone}
    @DeleteMapping("/{zone}")
    public ResponseEntity<Void> delete(@PathVariable String zone) {
        if (zonePricingService.getByZone(zone).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        zonePricingService.delete(zone);
        return ResponseEntity.noContent().build();
    }
}
