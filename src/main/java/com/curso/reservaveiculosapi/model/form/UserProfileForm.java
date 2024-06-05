package com.curso.reservaveiculosapi.model.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


public record UserProfileForm(
        @NotNull(message = "ID do usuário é obrigatório")
        Long userId,

        @NotNull(message = "Id do perfil é obrigatório")
        List<Long> profilesId
) {


}
