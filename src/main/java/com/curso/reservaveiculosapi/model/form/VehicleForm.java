package com.curso.reservaveiculosapi.model.form;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Builder
public record VehicleForm(

        String name,

        String maker,

        String type,


        BigDecimal price

) {
}
