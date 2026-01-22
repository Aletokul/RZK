package com.example.parkinglotservice.repository;

import com.example.parkinglotservice.model.ZonePricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZonePricingRepository extends JpaRepository<ZonePricing, String> {

    Optional<ZonePricing> findByZone(String zone);

}
