package com.example.parkinglotservice.service;

import com.example.parkinglotservice.model.ZonePricing;
import com.example.parkinglotservice.repository.ZonePricingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZonePricingService {

    private final ZonePricingRepository zonePricingRepository;

    public ZonePricingService(ZonePricingRepository zonePricingRepository) {
        this.zonePricingRepository = zonePricingRepository;
    }

    public List<ZonePricing> getAll() {
        return zonePricingRepository.findAll();
    }

    public Optional<ZonePricing> getByZone(String zone) {
        return zonePricingRepository.findById(zone);
    }

    public ZonePricing create(ZonePricing zonePricing) {
        return zonePricingRepository.save(zonePricing);
    }

    public ZonePricing update(ZonePricing zonePricing) {
        return zonePricingRepository.save(zonePricing);
    }

    public void delete(String zone) {
        zonePricingRepository.deleteById(zone);
    }
}
