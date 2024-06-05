package com.curso.reservaveiculosapi.model.dto;

import com.curso.reservaveiculosapi.model.entity.ImageVehicleEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleEntity;

import java.math.BigDecimal;
import java.util.List;

public record VehicleResponse(

        Long id,
        String name,

        String maker,

        String type,

        BigDecimal price,

        List<ImageResponse> imageVehicles


) {
    public VehicleResponse(VehicleEntity vehicle) {
        this(vehicle.getId(), vehicle.getName(), vehicle.getMaker(), vehicle.getType(),
                vehicle.getPrice(),
                vehicle.getImages()
                        .stream()
                        .map(ImageResponse::new).toList());
    }




}
