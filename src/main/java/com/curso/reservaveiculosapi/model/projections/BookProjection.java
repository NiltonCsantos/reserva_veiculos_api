package com.curso.reservaveiculosapi.model.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface BookProjection {

   Long getCount();

   @JsonFormat(pattern = "dd/MM/yyyy")
   LocalDate getDate();


}
