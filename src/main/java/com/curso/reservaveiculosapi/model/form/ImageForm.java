package com.curso.reservaveiculosapi.model.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageForm(
        @NotBlank(message = "O campo nome da imagem não pode ser em branco")
        String name,
        @NotNull(message = "O valor em binário não pode ser nulo")
        byte[] bytes,
        @NotBlank(message = "O tipo da imagem não pode ser nulo")
        String extension,


        Long idImage
) {
}
