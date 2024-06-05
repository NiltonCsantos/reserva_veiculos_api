package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import com.curso.reservaveiculosapi.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByName(ProfileRole role);

}
