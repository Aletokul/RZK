package com.example.reservationservice.controller;

import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations") // po želji /api/reservations
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //GET /reservations
    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    //GET /reservations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return reservationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET /reservations/by-user/{userId}
    @GetMapping("/by-user/{userId}")
    public List<Reservation> getByUser(@PathVariable Long userId) {
        return reservationService.getByUserId(userId);
    }

    //POST /reservations
    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation saved = reservationService.create(reservation);
        return ResponseEntity.ok(saved);
    }

    //PUT /reservations/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable Long id,
            @RequestBody Reservation updated
    ) {
        return reservationService.getById(id)
                .map(existing -> {
                    existing.setUserId(updated.getUserId());
                    existing.setLotId(updated.getLotId());
                    existing.setSpotId(updated.getSpotId());
                    existing.setStartTime(updated.getStartTime());
                    existing.setEndTime(updated.getEndTime());
                    existing.setTotalPrice(updated.getTotalPrice());
                    existing.setStatus(updated.getStatus());
                    Reservation saved = reservationService.update(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE /reservations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reservationService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //POST /reservations/{id}/confirm
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Reservation> confirm(@PathVariable Long id) {
        return reservationService.confirm(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST /reservations/{id}/cancel
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancel(@PathVariable Long id) {
        return reservationService.cancel(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getForUser(@PathVariable Long userId) {
        return reservationService.findByUserId(userId);
    }

}
