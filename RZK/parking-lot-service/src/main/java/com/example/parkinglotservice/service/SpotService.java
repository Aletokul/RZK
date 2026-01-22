package com.example.parkinglotservice.service;

import com.example.parkinglotservice.model.Spot;
import com.example.parkinglotservice.repository.SpotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpotService {

    private final SpotRepository spotRepository;
    private final LotService lotService;

    public SpotService(SpotRepository spotRepository, LotService lotService) {
        this.spotRepository = spotRepository;
        this.lotService = lotService;
    }

    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    public Optional<Spot> getSpotById(Long id) {
        return spotRepository.findById(id);
    }

    public List<Spot> getSpotsByLot(Long lotId) {
        return spotRepository.findByLotId(lotId);
    }

    public List<Spot> getFreeSpotsByLot(Long lotId) {
        return spotRepository.findByLotIdAndOccupiedFalse(lotId);
    }

    public List<Spot> getEvSpotsByLot(Long lotId) {
        return spotRepository.findByLotIdAndHasEvTrue(lotId);
    }

    public Spot createSpot(Long lotId, Spot spot) {
        var lot = lotService.getLotById(lotId)
                .orElseThrow(() -> new IllegalArgumentException("Lot not found with id " + lotId));

        spot.setLot(lot);
        spot.setCreatedAt(LocalDateTime.now());
        spot.setOccupied(false);
        return spotRepository.save(spot);
    }

    public Spot updateSpot(Spot spot) {
        return spotRepository.save(spot);
    }

    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return spotRepository.existsById(id);
    }
}
