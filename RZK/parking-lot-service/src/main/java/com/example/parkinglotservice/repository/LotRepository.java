package com.example.parkinglotservice.repository;

import com.example.parkinglotservice.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {

    Optional<Lot> findByName(String name);

    List<Lot> findByZone(String zone);
}
