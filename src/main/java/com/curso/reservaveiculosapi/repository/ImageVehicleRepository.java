package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.model.entity.ImageVehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageVehicleRepository extends JpaRepository<ImageVehicleEntity, Long> {
}
