package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import com.curso.reservaveiculosapi.exceptions.NotFoundException;
import com.curso.reservaveiculosapi.exceptions.RegistredException;
import com.curso.reservaveiculosapi.model.dto.BookResponse;
import com.curso.reservaveiculosapi.model.dto.UserResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.entity.*;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.UserProfileForm;
import com.curso.reservaveiculosapi.model.projections.BookProjection;
import com.curso.reservaveiculosapi.repository.ProfileRepository;
import com.curso.reservaveiculosapi.repository.UserRepository;
import com.curso.reservaveiculosapi.repository.VehicleRepository;
import com.curso.reservaveiculosapi.repository.VehicleUserRepository;
import com.curso.reservaveiculosapi.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleUserRepository vehicleUserRepository;

    private final AuthenticateService authenticateService;

    @Override
    public void cadastreProfile(ProfileRole profile) throws RegistredException {

        Optional<ProfileEntity> profileFind = this.profileRepository.findByName(profile);

        if (profileFind.isPresent()){
            throw  new RegistredException("Já existe um perfil cadastrado " +
                    "com o nome: "+profile.getProfile());
        }


        ProfileEntity profileEntity = ProfileEntity.builder()
                .name(profile)
                .build();

        profileRepository.save(profileEntity);

    }

    @Transactional
    @Override
    public Page<UserResponse> getAllUserPaginated(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::new);
    }

    @Transactional
    @Override
    public void addProfileToUser(UserProfileForm userProfileForm) {

        UserEntity user = this.findUser(userProfileForm.userId());

        if (this.isProfilePertenceToUser(user.getProfiles(), userProfileForm.profilesId())){
            throw new RegistredException("O usuário: "+user.getName()+", já possui esse perfil");
        }

        userProfileForm.profilesId().forEach(profileId -> user.getProfiles().add(profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("Perfil não encontrado"))));


        userRepository.save(user);



    }

    //TODO: Melhorias: Impedir que usuários diferentes reservem o mesmo veículo

    @Override
    public void BookVehicle(BookVehicleForm vehicleForm) {

        Long id = authenticateService.getUser().getId();

       UserEntity user = this.findUser(id);

       VehicleEntity vehicle = vehicleRepository.findById(vehicleForm.vehicleId())
               .orElseThrow(() -> new NotFoundException("Veículo nãp encontrado"));




      if (this.vehicleUserRepository.findByVehicleIdAndUserId
              (vehicle.getId(), user.getId()).isPresent()){
          throw new RegistredException("Você já possui esse veículo reservado");
      }

      VehicleUserEntity vehicleUserEntity = VehicleUserEntity.builder()
              .user(user)
              .vehicle(vehicle)
              .date(LocalDate.now())
              .build();

      vehicleUserRepository.save(vehicleUserEntity);







    }

    @Override
    public List<VehicleResponse> getReserve() {
       UserEntity user = userRepository.findById(authenticateService.getUser().getId())
               .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

       return user.getVehicles().stream().map(VehicleUserEntity::getVehicle).map(VehicleResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<BookProjection> getAllReserves() {

        List<BookProjection> bookProjection = userRepository.findReserveUsersToProfileUser(ProfileRole.USER);

        return bookProjection;
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public void associateImageToVehicle() {

    }

    protected UserEntity findUser(Long userId) throws NotFoundException {

        return this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));



    }

    private boolean isProfilePertenceToUser(List<ProfileEntity> profileEntities, List<Long> profilesId){

       return profileEntities.stream()
                .map(ProfileEntity::getId)
                .anyMatch(profilesId::contains);

    }

    private boolean isVehiclePertenceToUser(List<VehicleUserEntity> vehiclesUser,
                                            List<Long> vehiclesId){

        return vehiclesUser.stream()
                .map(VehicleUserEntity::getId)
                .anyMatch(vehiclesId::contains);

    }

}
