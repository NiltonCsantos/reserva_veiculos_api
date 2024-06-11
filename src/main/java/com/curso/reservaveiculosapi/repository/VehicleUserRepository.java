package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.model.entity.UserEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleUserRepository extends JpaRepository<VehicleUserEntity, Long> {

    Optional<VehicleUserEntity> findByVehicleIdAndUserId(Long vehicleId, Long userId);



}
