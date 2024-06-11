package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.model.dto.ProfileResponse;
import com.curso.reservaveiculosapi.model.entity.ProfileEntity;

import java.util.List;

public interface IProfileService {

    Long countProfile();

    List<ProfileResponse> getProfiles();

}
