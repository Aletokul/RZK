package com.example.parkinglotservice.controller;

import com.example.parkinglotservice.model.Lot;
import com.example.parkinglotservice.service.LotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    // GET /lots
    @GetMapping
    public List<Lot> getAll() {
        return lotService.getAllLots();
    }

    // GET /lots/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Lot> getById(@PathVariable Long id) {
        return lotService.getLotById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /lots
    @PostMapping
    public ResponseEntity<Lot> create(@RequestBody Lot lot) {
        Lot saved = lotService.createLot(lot);
        return ResponseEntity.ok(saved);
    }

    // PUT /lots/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Lot> update(@PathVariable Long id, @RequestBody Lot updated) {
        return lotService.getLotById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setZone(updated.getZone());
                    existing.setAddress(updated.getAddress());
                    existing.setCapacity(updated.getCapacity());
                    Lot saved = lotService.updateLot(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /lots/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lotService.deleteLot(id);
        return ResponseEntity.noContent().build();
    }
}
