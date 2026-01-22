package com.example.parkinglotservice.service;

import com.example.parkinglotservice.model.Lot;
import com.example.parkinglotservice.repository.LotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotService {

    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }

    public Optional<Lot> getLotById(Long id) {
        return lotRepository.findById(id);
    }

    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public Lot updateLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }
}
