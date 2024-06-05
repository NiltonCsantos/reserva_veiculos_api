package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
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
    public ResponseEntity<Void> cadastreVehicle(@RequestBody @Valid VehicleForm vehicleForm){

        vehicleService.cadastreVehicle(vehicleForm);

        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/atualizar/{vehicleId}")
    public ResponseEntity<Void> updateVehicle(@RequestBody @Valid VehicleForm vehicleForm,
                                              @PathVariable Long vehicleId){
        this.vehicleService.updateVehicle(vehicleForm, vehicleId);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<VehicleResponse>> ListALlVehicles(Pageable pageable){
        return ResponseEntity.ok(vehicleService.getAllVehiclePaginated(pageable));
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

        vehicleService.saveImage(vehicleID, imageForm );

        return ResponseEntity.status(201).build();

    }

    @PutMapping("/imagem/atualizar/{vehicleID}")
    public ResponseEntity<Void> updateImage(@PathVariable Long vehicleID, @Valid @RequestBody ImageForm imageUpdateForm){

        vehicleService.updateImage(vehicleID, imageUpdateForm);

        return ResponseEntity.status(204).build();

    }

    @DeleteMapping("/imagem/deletar/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){


        return ResponseEntity.status(204).build();

    }




}
