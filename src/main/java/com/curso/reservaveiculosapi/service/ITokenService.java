package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.model.entity.UserEntity;

public interface ITokenService {
    String generateToken(UserEntity userEntity);

    String generateRefrashToken(UserEntity userEntity);

    String validateToken(String token);

    String validateRefrashToken(String refreshToken);
}
