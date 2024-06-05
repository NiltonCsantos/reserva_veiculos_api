package com.curso.reservaveiculosapi.model.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginForm(
        @NotBlank(message = "O campo email deve ser preenchido")
        @Email
        String email,

        @NotBlank(message = "O campo senha deve ser preenchido")
        String password
) {

}
