package com.curso.reservaveiculosapi.enuns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ProfileRole {

    ADMIN ("Administrador"),

    USER ("Cliente"),

    Seller("Vendedor"),

    GUEST("Visitante");

    private String profile;

}
