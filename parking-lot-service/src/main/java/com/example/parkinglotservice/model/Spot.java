package com.example.parkinglotservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "spots",
        uniqueConstraints = @UniqueConstraint(name = "uq_spot", columnNames = {"lot_id", "number"})
)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false, foreignKey = @ForeignKey(name = "fk_spot_lot"))
    @JsonIgnore
    private Lot lot;

    @Column(nullable = false, length = 20)
    private String number;

    @Column(name = "is_occupied", nullable = false)
    private boolean occupied;

    @Column(name = "has_ev", nullable = false)
    private boolean hasEv;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Spot() {
    }

    public Spot(Lot lot, String number, boolean occupied, boolean hasEv) {
        this.lot = lot;
        this.number = number;
        this.occupied = occupied;
        this.hasEv = hasEv;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isHasEv() {
        return hasEv;
    }

    public void setHasEv(boolean hasEv) {
        this.hasEv = hasEv;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @JsonProperty("lotId")
    public Long getLotId() {
        return lot != null ? lot.getId() : null;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", lotId=" + (lot != null ? lot.getId() : null) +
                ", number='" + number + '\'' +
                ", occupied=" + occupied +
                ", hasEv=" + hasEv +
                ", createdAt=" + createdAt +
                '}';
    }
}
