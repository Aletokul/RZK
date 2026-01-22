package com.example.reservationservice.dto;

public class SpotDto {

    private Long id;
    private Long lotId;
    private String number;
    private boolean hasEv;
    private boolean occupied;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isHasEv() {
        return hasEv;
    }

    public void setHasEv(boolean hasEv) {
        this.hasEv = hasEv;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
