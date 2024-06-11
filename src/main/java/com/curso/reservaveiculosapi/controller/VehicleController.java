package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.model.dto.SellerResponse;
import com.curso.reservaveiculosapi.model.dto.UserResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.FilterVehicle;
import com.curso.reservaveiculosapi.model.form.ImageForm;
import com.curso.reservaveiculosapi.model.form.VehicleForm;
import com.curso.reservaveiculosapi.service.IVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veiculo")
public class VehicleController {

    private final IVehicleService vehicleService;
    @PostMapping()
    public ResponseEntity<Long> cadastreVehicle(@RequestBody @Valid VehicleForm vehicleForm){
        System.out.println();
        return ResponseEntity.status(201).body(vehicleService.cadastreVehicle(vehicleForm));
    }

    @PatchMapping("/atualizar/{vehicleId}")
    public ResponseEntity<Long> updateVehicle(@RequestBody @Valid VehicleForm vehicleForm,
                                              @PathVariable Long vehicleId){

        System.out.println("RETORNANDO");

        return ResponseEntity.status(200).body( this.vehicleService.updateVehicle(vehicleForm, vehicleId));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<VehicleResponse>> ListALlVehicles(Pageable pageable, FilterVehicle vehicle){
        return ResponseEntity.ok(vehicleService.getAllVehiclePaginated(pageable, vehicle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable Long id){
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }



    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        this.vehicleService.deleteVehicle(id);

        return ResponseEntity.status(204).build();
    }

    @PostMapping("/imagem/salvar/{vehicleID}")
    public ResponseEntity<Void> saveImage(@PathVariable Long vehicleID,
                                          @RequestBody @Valid ImageForm imageForm){

        System.out.println("BUSCANDO Véiculo por id ");
        System.out.println(vehicleID);

        vehicleService.saveImage(vehicleID, imageForm );

        return ResponseEntity.status(201).build();

    }

    @PutMapping("/imagem/atualizar/{vehicleID}")
    public ResponseEntity<Void> updateImage(@PathVariable Long vehicleID, @Valid @RequestBody ImageForm imageUpdateForm){

        vehicleService.updateImage(vehicleID, imageUpdateForm);

        return ResponseEntity.status(204).build();

    }

    @DeleteMapping("/imagem/deletar/{vehicleId}/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long vehicleId,  @PathVariable Long imageId ){
        vehicleService.deleteImage(vehicleId, imageId);
        return ResponseEntity.status(204).build();

    }

    @GetMapping("/count")
    public  ResponseEntity<Long> countVehicles(){
        return ResponseEntity.ok(this.vehicleService.countVehicles());
    }


    @GetMapping("/info-seller/{vehicleId}")
    ResponseEntity<SellerResponse> getSellerInfo(@PathVariable Long vehicleId){
        return ResponseEntity.ok(vehicleService.getInfoSeller(vehicleId));
    }


}
