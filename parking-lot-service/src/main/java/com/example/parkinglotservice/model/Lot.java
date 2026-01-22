package com.example.parkinglotservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 10)
    private String zone;

    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 200)
    private String address;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;



    public Lot() {
    }

    public Lot(String name, String zone, Integer capacity, String address) {
        this.name = name;
        this.zone = zone;
        this.capacity = capacity;
        this.address = address;
    }

    public Lot(Long id, String name, String zone, Integer capacity, String address, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.capacity = capacity;
        this.address = address;
        this.createdAt = createdAt;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zone='" + zone + '\'' +
                ", capacity=" + capacity +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
