package com.curso.reservaveiculosapi.model.form;

import jakarta.validation.constraints.NotNull;

public record BookVehicleForm(

        @NotNull(message = "O id do veículo não pode ser nulo")
        Long vehicleId
) {
}
