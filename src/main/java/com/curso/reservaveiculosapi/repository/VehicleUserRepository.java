package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleUserRepository extends JpaRepository<VehicleUserEntity, Long> {

    Optional<VehicleUserEntity>findByVehicleIdAndUserId(Long vehicleId, Long userId);

}
