package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.exceptions.ImageVehicleException;
import com.curso.reservaveiculosapi.exceptions.NotFoundException;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.entity.ImageVehicleEntity;
import com.curso.reservaveiculosapi.model.entity.UserEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.ImageForm;
import com.curso.reservaveiculosapi.model.form.ImageUpdateForm;
import com.curso.reservaveiculosapi.model.form.VehicleForm;
import com.curso.reservaveiculosapi.repository.VehicleRepository;
import com.curso.reservaveiculosapi.repository.VehicleUserRepository;
import com.curso.reservaveiculosapi.service.IVehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserService userService;



    private final VehicleUserRepository vehicleUserRepository;

    private final AuthenticateService authenticateService;


    @Override
    public void cadastreVehicle(VehicleForm vehicleForm) {

        UserEntity user = userService.findUser(authenticateService.getUser().getId());

        VehicleEntity vehicle = VehicleEntity
                .builder()
                .name(vehicleForm.name())
                .type(vehicleForm.type())
                .maker(vehicleForm.maker())
                .users(new ArrayList<>())
                .images(new ArrayList<>())
                .price(vehicleForm.price())
                .build();

        VehicleUserEntity vehicleUserEntity = VehicleUserEntity
                .builder()
                .user(user)
                .vehicle(vehicle)
                .date(LocalDate.now())
                .build();

        this.vehicleUserRepository.save(vehicleUserEntity);

        System.out.println(user.getVehicles().size());


    }

    @Override
    public void updateVehicle(VehicleForm vehicleForm, Long vehicleId) throws NotFoundException {

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(), vehicleId);

        System.out.println(isUserContainsVehicle);

        VehicleEntity vehicle = this.findVehicle(vehicleId);

        if (!isUserContainsVehicle) {
            throw new NotFoundException("Você não pode alterar os dados desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }

        System.out.println(vehicleForm);

        vehicle.setType(vehicleForm.type());
        vehicle.setName(vehicleForm.name());
        vehicle.setMaker(vehicleForm.maker());
        vehicle.setPrice(vehicleForm.price());

        System.out.println(vehicle.getPrice());

        vehicleRepository.save(vehicle);

    }

    @Transactional
    @Override
    public void deleteVehicle(Long id) {

        System.out.println("ID DO Veiculo");

        System.out.println(id);

        Long userId = userService.findUser(authenticateService.getUser().getId()).getId();

        UserEntity user = userService.findUser(userId);

        VehicleEntity vehicle = this.findVehicle(id);

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(userId).getVehicles(), id);


        if (!isUserContainsVehicle) {
            throw new NotFoundException("Você não pode deletar os dados desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }

        System.out.println(vehicle.getName());
        this.vehicleRepository.delete(vehicle);
        System.out.println("DELETADO");



    }

    @Override
    public Page<VehicleResponse> getAllVehiclePaginated(Pageable pageable) {
        return this.vehicleRepository.findAll(pageable).map(VehicleResponse::new);
    }

    @Override
    public VehicleResponse getVehicle(Long id) {
        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        return new VehicleResponse(vehicle);
    }

    @Override
    public void saveImage(Long vehicleID, ImageForm imageForm) {

        VehicleEntity vehicle = this.findVehicle(vehicleID);

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(), vehicleID);


        if (!isUserContainsVehicle) {
            throw new NotFoundException("Você não pode adicionar imagens a esse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }

        if (vehicle.getImages().size() >= 6) {
            throw new ImageVehicleException("O veículo atingiu o máximo de imagens permitidas");
        }

        if (vehicle.getImages().stream().anyMatch(image -> Arrays.equals(image.getBytes(), imageForm.bytes()))) {
            throw new ImageVehicleException("Esse veiculo já possui essa imagem");
        }


        ImageVehicleEntity newImage = ImageVehicleEntity.builder()
                .name(imageForm.name())
                .vehicle(vehicle)
                .extension(imageForm.extension())
                .bytes(imageForm.bytes())
                .build();

        vehicle.getImages().add(newImage);

        vehicleRepository.save(vehicle);


    }

    @Override
    public void updateImage(Long vehicleID, ImageForm imageUpdateForm) {

        VehicleEntity vehicle = this.findVehicle(vehicleID);

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(),
                        vehicleID);


        if (!isUserContainsVehicle) {
            throw new NotFoundException("Você não pode atualizar imagens  desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }


        ImageVehicleEntity imageVehicleEntity = vehicle.getImages().stream()
                .filter(image -> image.getId().equals(imageUpdateForm.idImage()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Imagem não encontrada"));

        imageVehicleEntity.setBytes(imageUpdateForm.bytes());
        imageVehicleEntity.setExtension(imageUpdateForm.extension());
        imageVehicleEntity.setName(imageUpdateForm.name());


        vehicleRepository.save(vehicle);

    }

    @Override
    public void deleteImage(Long id) {



    }

    private VehicleEntity findVehicle(Long vehicleId) throws NotFoundException {
        return this.vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
    }

    private boolean userContainsVehicle(List<VehicleUserEntity> vehicleUserEntities, Long vehicleId) {
        return vehicleUserEntities
                .stream()
                .anyMatch(vehicleUserEntity -> vehicleUserEntity.getVehicle().getId().equals(vehicleId));
    }


}
