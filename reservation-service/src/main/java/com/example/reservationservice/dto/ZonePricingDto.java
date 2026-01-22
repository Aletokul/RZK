package com.example.reservationservice.dto;

import java.math.BigDecimal;

public class ZonePricingDto {

    private String zone;
    private BigDecimal pricePerHour;
    private Integer roundToMinutes;

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
}
