package com.example.parkinglotservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "zone_pricing")
public class ZonePricing {

    @Id
    @Column(length = 10)
    private String zone;

    @Column(name = "price_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "round_to_minutes", nullable = false)
    private Integer roundToMinutes;


    public ZonePricing() {
    }

    public ZonePricing(String zone, BigDecimal pricePerHour, Integer roundToMinutes) {
        this.zone = zone;
        this.pricePerHour = pricePerHour;
        this.roundToMinutes = roundToMinutes;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getRoundToMinutes() {
        return roundToMinutes;
    }

    public void setRoundToMinutes(Integer roundToMinutes) {
        this.roundToMinutes = roundToMinutes;
    }

    @Override
    public String toString() {
        return "ZonePricing{" +
                "zone='" + zone + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", roundToMinutes=" + roundToMinutes +
                '}';
    }
}
