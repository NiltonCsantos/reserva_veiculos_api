package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {



}
