package com.curso.reservaveiculosapi.model.dto;

import com.curso.reservaveiculosapi.model.entity.UserEntity;

public record SellerResponse(

        Long id,

        String name,

        String email
) {
    public SellerResponse (UserEntity userEntity){
        this(userEntity.getId(), userEntity.getName(), userEntity.getLogin());
    }
}
