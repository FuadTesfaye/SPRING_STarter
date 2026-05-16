package com.example.inventory.infrastructure.persistence;

import com.example.inventory.application.port.out.ReservationRepository;
import com.example.inventory.domain.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservationPersistenceAdapter implements ReservationRepository {

    private final SpringDataReservationRepository springDataReservationRepository;

    public ReservationPersistenceAdapter(SpringDataReservationRepository springDataReservationRepository) {
        this.springDataReservationRepository = springDataReservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity(
                reservation.getOrderId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
        if (reservation.getId() != null) {
            entity.setId(reservation.getId());
        }
        ReservationEntity saved = springDataReservationRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Reservation> findByOrderId(Long orderId) {
        return springDataReservationRepository.findByOrderId(orderId)
                .map(this::toDomain);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return springDataReservationRepository.findById(id)
                .map(this::toDomain);
    }

    private Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.getId(),
                entity.getOrderId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
