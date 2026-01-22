package com.example.parkinglotservice.repository;

import com.example.parkinglotservice.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    // Svi spotovi za dati lot
    @Query("SELECT s FROM Spot s WHERE s.lot.id = :lotId")
    List<Spot> findByLotId(@Param("lotId") Long lotId);

    // EV spotovi (hasEv = true) za dati lot
    @Query("SELECT s FROM Spot s WHERE s.lot.id = :lotId AND s.hasEv = true")
    List<Spot> findByLotIdAndHasEvTrue(@Param("lotId") Long lotId);

    // Slobodna mesta (occupied = false) za dati lot
    @Query("SELECT s FROM Spot s WHERE s.lot.id = :lotId AND s.occupied = false")
    List<Spot> findByLotIdAndOccupiedFalse(@Param("lotId") Long lotId);
}
