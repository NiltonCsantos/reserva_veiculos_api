package com.curso.reservaveiculosapi.service.impl;

import com.curso.reservaveiculosapi.exceptions.BadRequestException;
import com.curso.reservaveiculosapi.exceptions.ImageVehicleException;
import com.curso.reservaveiculosapi.exceptions.NotFoundException;
import com.curso.reservaveiculosapi.exceptions.UnauthorizedException;
import com.curso.reservaveiculosapi.model.dto.SellerResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.entity.ImageVehicleEntity;
import com.curso.reservaveiculosapi.model.entity.UserEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.form.FilterVehicle;
import com.curso.reservaveiculosapi.model.form.ImageForm;
import com.curso.reservaveiculosapi.model.form.VehicleForm;
import com.curso.reservaveiculosapi.model.projections.SellerProjection;
import com.curso.reservaveiculosapi.repository.ImageVehicleRepository;
import com.curso.reservaveiculosapi.repository.VehicleRepository;
import com.curso.reservaveiculosapi.repository.VehicleUserRepository;
import com.curso.reservaveiculosapi.service.IVehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final VehicleUserRepository vehicleUserRepository;

    private final AuthenticateService authenticateService;

    private final ImageVehicleRepository imageVehicleRepository;


    @Override
    @Transactional
    public Long cadastreVehicle(VehicleForm vehicleForm) {

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

        System.out.println("ID DE RETORNO");
        System.out.println();

        return this.vehicleUserRepository.save(vehicleUserEntity).getVehicle().getId();

    }

    @Override
    @Transactional
    public Long updateVehicle(VehicleForm vehicleForm, Long vehicleId) throws NotFoundException {



        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(), vehicleId);


        VehicleEntity vehicle = this.findVehicle(vehicleId);

        if (!isUserContainsVehicle) {
            throw new NotFoundException("Você não pode alterar os dados desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }


        vehicle.setType(vehicleForm.type());
        vehicle.setName(vehicleForm.name());
        vehicle.setMaker(vehicleForm.maker());
        vehicle.setPrice(vehicleForm.price());


         vehicleRepository.save(vehicle);

        System.out.println("ID DO VEICULO: "+ vehicleId);

         return vehicleId;

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
    @Transactional
    public Page<VehicleResponse> getAllVehiclePaginated(Pageable pageable, FilterVehicle vehicle) {
        return this.vehicleRepository.findAllByFilters(pageable, vehicle).map(VehicleResponse::new);
    }

    @Override
    @Transactional
    public VehicleResponse getVehicle(Long id) {
        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        return new VehicleResponse(vehicle);
    }

    @Override
    @Transactional
    public void saveImage(Long vehicleID, ImageForm imageForm) {

        System.out.println("CADASTRANDO IMAGEM");

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
    @Transactional
    public void updateImage(Long vehicleID, ImageForm imageUpdateForm) throws UnauthorizedException {

        VehicleEntity vehicle = this.findVehicle(vehicleID);

        System.out.println("DATA");
        System.out.println(imageUpdateForm);

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(),
                        vehicleID);


        if (!isUserContainsVehicle) {
            throw new UnauthorizedException("Você não pode atualizar imagens  desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }


        ImageVehicleEntity imageVehicleEntity =  verifiFyVehicleContainsImage(vehicle.getId(), imageUpdateForm.idImage());

        imageVehicleEntity.setBytes(imageUpdateForm.bytes());
        imageVehicleEntity.setExtension(imageUpdateForm.extension());
        imageVehicleEntity.setName(imageUpdateForm.name());


        vehicleRepository.save(vehicle);

    }

    @Override
    @Transactional
    public void deleteImage(Long vehicleID, Long imageId) throws UnauthorizedException {

        VehicleEntity vehicle = this.findVehicle(vehicleID);

        boolean isUserContainsVehicle =
                this.userContainsVehicle(userService.findUser(authenticateService.getUser().getId()).getVehicles(),
                        vehicleID);


        if (!isUserContainsVehicle) {
            throw new UnauthorizedException("Você não pode atualizar imagens  desse véiculo, devido ao " +
                    "motivo de não estar vínculado(a) a ele");
        }

        ImageVehicleEntity imageVehicleEntity = verifiFyVehicleContainsImage(vehicle.getId(), imageId);


        imageVehicleRepository.delete(imageVehicleEntity);

    }


    private VehicleEntity findVehicle(Long vehicleId) throws NotFoundException {
        return this.vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
    }


    @Transactional
    @Override
    public Long countVehicles() {
        return vehicleRepository.count();
    }

    @Override
    public SellerResponse getInfoSeller(Long vehicleId) {
        SellerProjection user= vehicleRepository.findBySellerInfo(vehicleId);

        System.out.println(user.getLogin());

        return new SellerResponse(
                user.getId(),
                user.getName(),
                user.getLogin()
        );

    }

    private ImageVehicleEntity verifiFyVehicleContainsImage(Long vehicleId, Long imageId)
    throws NotFoundException, UnauthorizedException {

        System.out.println("IMAGEM");
        System.out.println(imageId);



       ImageVehicleEntity imageVehicleEntity = imageVehicleRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Imagem não encontrada"));

       if (!imageVehicleEntity.getVehicle().getId().equals(vehicleId)){
           throw new UnauthorizedException("A imagem não pertecente a esse veículo");
       }

       return imageVehicleEntity;

    }
    private boolean userContainsVehicle(List<VehicleUserEntity> vehicleUserEntities, Long vehicleId) {
        return vehicleUserEntities
                .stream()
                .anyMatch(vehicleUserEntity -> vehicleUserEntity.getVehicle().getId().equals(vehicleId));
    }


}
