package com.curso.reservaveiculosapi.model.form;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import javax.management.relation.Role;

@Builder
public record UserRegisterForm(

        @NotBlank(message = "O campo nome deve ser preenchido")
        String fullName,

        @NotBlank(message = "O campo email deve ser preenchido")
        @Email
        String email,

        @NotBlank(message = "O campo senha deve ser preenchido")
        String password,

        ProfileRole profile
        
) {
}
