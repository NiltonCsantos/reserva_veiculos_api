package com.curso.reservaveiculosapi.model.dto;

import com.curso.reservaveiculosapi.model.entity.ImageVehicleEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleEntity;
import jakarta.persistence.*;

public record ImageResponse(
        Long id,
        byte[] bytes,

        String extension
) {

    public ImageResponse(ImageVehicleEntity imageVehicleEntity) {
        this(imageVehicleEntity.getId(), imageVehicleEntity.getBytes(), imageVehicleEntity.getExtension());
    }
}
