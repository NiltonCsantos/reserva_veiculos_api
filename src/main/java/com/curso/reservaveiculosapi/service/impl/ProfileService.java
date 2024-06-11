package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.model.dto.ProfileResponse;
import com.curso.reservaveiculosapi.model.entity.ProfileEntity;
import com.curso.reservaveiculosapi.repository.ProfileRepository;
import com.curso.reservaveiculosapi.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private  final ProfileRepository profileRepository;
    @Override
    public Long countProfile() {
        return profileRepository.count();
    }

    @Override
    public List<ProfileResponse> getProfiles() {
        return profileRepository.findAll()
                .stream().map(ProfileResponse::new)
                .toList();
    }
}
