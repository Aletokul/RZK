package com.example.parkinglotservice.controller;

import com.example.parkinglotservice.model.Spot;
import com.example.parkinglotservice.service.SpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spots")
public class SpotController {

    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    // GET /spots
    @GetMapping
    public List<Spot> getAllSpots() {
        return spotService.getAllSpots();
    }

    // GET /spots/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Spot> getSpotById(@PathVariable Long id) {
        Optional<Spot> spot = spotService.getSpotById(id);
        return spot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /spots/by-lot/{lotId}
    @GetMapping("/by-lot/{lotId}")
    public List<Spot> getSpotsByLot(@PathVariable Long lotId) {
        return spotService.getSpotsByLot(lotId);
    }

    // GET /spots/by-lot/{lotId}/free
    @GetMapping("/by-lot/{lotId}/free")
    public List<Spot> getFreeSpots(@PathVariable Long lotId) {
        return spotService.getFreeSpotsByLot(lotId);
    }

    // GET /spots/by-lot/{lotId}/ev
    @GetMapping("/by-lot/{lotId}/ev")
    public List<Spot> getEvSpots(@PathVariable Long lotId) {
        return spotService.getEvSpotsByLot(lotId);
    }

    // POST /spots
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Spot createSpot(@RequestParam Long lotId, @RequestBody Spot spot) {
        return spotService.createSpot(lotId, spot);
    }

    // PUT /spots/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Spot> updateSpot(@PathVariable Long id, @RequestBody Spot updatedSpot) {
        return spotService.getSpotById(id)
                .map(existing -> {
                    existing.setNumber(updatedSpot.getNumber());
                    existing.setOccupied(updatedSpot.isOccupied());
                    existing.setHasEv(updatedSpot.isHasEv());
                    Spot saved = spotService.updateSpot(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /spots/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        if (!spotService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        spotService.deleteSpot(id);
        return ResponseEntity.noContent().build();
    }
}
