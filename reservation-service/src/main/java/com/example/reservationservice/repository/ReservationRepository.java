package com.example.reservationservice.repository;

import com.example.reservationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByLotId(Long lotId);

    List<Reservation> findBySpotId(Long spotId);

    List<Reservation> findBySpotIdAndStatus(Long spotId, Reservation.ReservationStatus status);
}
