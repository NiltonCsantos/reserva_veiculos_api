package com.curso.reservaveiculosapi.model.dto;

import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.projections.BookProjection;

import java.time.LocalDate;

public class BookResponse implements BookProjection{

    Long count;

    LocalDate date;

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public BookResponse(VehicleUserEntity vehicleUserEntity) {
        this.count = vehicleUserEntity.getId();
        this.date = vehicleUserEntity.getDate();
    }
}
