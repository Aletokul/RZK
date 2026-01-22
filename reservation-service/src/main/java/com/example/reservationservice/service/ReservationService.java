package com.example.reservationservice.service;

import com.example.reservationservice.dto.LotDto;
import com.example.reservationservice.dto.SpotDto;
import com.example.reservationservice.dto.ZonePricingDto;
import com.example.reservationservice.feign.ReservationProxy;
import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.repository.ReservationRepository;
import feign.FeignException;
import java.time.Duration;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationProxy reservationProxy;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationProxy reservationProxy) {
        this.reservationRepository = reservationRepository;
        this.reservationProxy = reservationProxy;
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation create(Reservation reservation) {

        if (reservation.getSpotId() == null ||
                reservation.getStartTime() == null ||
                reservation.getEndTime() == null) {
            throw new IllegalArgumentException("spotId, startTime i endTime su obavezni.");
        }

        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalArgumentException("startTime mora biti pre endTime.");
        }

        //provera da li spot postoji
        SpotDto spot;
        try {
            spot = reservationProxy.getSpotById(reservation.getSpotId());
            if (spot == null) {
                throw new IllegalArgumentException(
                        "Parking mesto sa ID " + reservation.getSpotId() + " ne postoji."
                );
            }
        } catch (FeignException e) {
            throw new IllegalArgumentException(
                    "Parking mesto sa ID " + reservation.getSpotId() + " ne postoji."
            );
        }

        //uzimanje lotid iz spota
        Long spotLotId = spot.getLotId();
        if (spotLotId == null) {
            throw new IllegalStateException("Parking mesto nema definisan lotId.");
        }

        // ako user nije poslao lotId postavlja ga iz spota
        if (reservation.getLotId() == null) {
            reservation.setLotId(spotLotId);
        } else if (!spotLotId.equals(reservation.getLotId())) {
            // ako jeste poslao lotId ali ne odgovara onom iz spota
            throw new IllegalArgumentException(
                    "Parking mesto sa ID " + reservation.getSpotId() +
                            " ne pripada parking lotu " + reservation.getLotId() + "."
            );
        }

        //provera da li lot postji i koja je zona
        LotDto lot;
        try {
            lot = reservationProxy.getLotById(reservation.getLotId());
            if (lot == null) {
                throw new IllegalArgumentException(
                        "Parking lot sa ID " + reservation.getLotId() + " ne postoji."
                );
            }
        } catch (FeignException e) {
            throw new IllegalArgumentException(
                    "Parking lot sa ID " + reservation.getLotId() + " ne postoji."
            );
        }

        String zone = lot.getZone();
        if (zone == null || zone.isBlank()) {
            throw new IllegalStateException("Parking lot nema definisanu zonu.");
        }

        //uzimanje cene iz zone-pricing-service
        ZonePricingDto pricing;
        try {
            pricing = reservationProxy.getZonePricingByZone(zone);
            if (pricing == null || pricing.getPricePerHour() == null) {
                throw new IllegalStateException("Nije definisana cena za zonu: " + zone);
            }
        } catch (FeignException e) {
            throw new IllegalStateException("Greška pri dohvatanju cene za zonu: " + zone);
        }

        //provera postojanja termina ako je rezervacija CONFIRMED
        boolean zauzeto = reservationRepository
                .findBySpotIdAndStatus(reservation.getSpotId(), Reservation.ReservationStatus.CONFIRMED)
                .stream()
                .anyMatch(r -> timeOverlap(
                        reservation.getStartTime(), reservation.getEndTime(),
                        r.getStartTime(), r.getEndTime()
                ));

        if (zauzeto) {
            throw new IllegalStateException("Ovo parking mesto je već rezervisano u tom terminu.");
        }

        //Status(default)
        reservation.setStatus(
                Optional.ofNullable(reservation.getStatus())
                        .orElse(Reservation.ReservationStatus.PENDING)
        );

        //Racunanje cene
        long minutes = Duration.between(
                reservation.getStartTime(),
                reservation.getEndTime()
        ).toMinutes();

        if (minutes <= 0) {
            throw new IllegalArgumentException("Trajanje rezervacije mora biti pozitivno.");
        }

        Integer roundTo = pricing.getRoundToMinutes();
        if (roundTo != null && roundTo > 0) {
            long mod = minutes % roundTo;
            if (mod != 0) {
                minutes += (roundTo - mod);
            }
        }

        BigDecimal hours = BigDecimal.valueOf(minutes)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.UP);

        BigDecimal pricePerHour = pricing.getPricePerHour();
        BigDecimal totalPrice = pricePerHour.multiply(hours)
                .setScale(2, RoundingMode.HALF_UP);

        reservation.setTotalPrice(totalPrice);
        reservation.setCreatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }






    //pomoćna metoda za proveru preklapanja termina
    private boolean timeOverlap(LocalDateTime start1, LocalDateTime end1,
                                LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    //promena status na CONFIRMED
    public Optional<Reservation> confirm(Long id) {
        return reservationRepository.findById(id)
                .map(res -> {
                    res.setStatus(Reservation.ReservationStatus.CONFIRMED);
                    return reservationRepository.save(res);
                });
    }

    //promena status na CANCELED
    public Optional<Reservation> cancel(Long id) {
        return reservationRepository.findById(id)
                .map(res -> {
                    res.setStatus(Reservation.ReservationStatus.CANCELED);
                    return reservationRepository.save(res);
                });
    }

    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

}
