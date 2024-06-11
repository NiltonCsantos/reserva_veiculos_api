package com.curso.reservaveiculosapi.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record FilterVehicle(
        Double price,

        String type
) {


}
