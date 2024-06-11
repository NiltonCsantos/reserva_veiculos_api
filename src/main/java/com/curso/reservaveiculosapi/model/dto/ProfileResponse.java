package com.curso.reservaveiculosapi.model.dto;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import com.curso.reservaveiculosapi.model.entity.ProfileEntity;

public record ProfileResponse(
        ProfileRole profile,

        Long Id

) {
    public ProfileResponse (ProfileEntity profileEntity){
        this(profileEntity.getName(), profileEntity.getId());
    }
}
