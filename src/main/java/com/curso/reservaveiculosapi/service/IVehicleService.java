package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.model.dto.SellerResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.FilterVehicle;
import com.curso.reservaveiculosapi.model.form.ImageForm;
import com.curso.reservaveiculosapi.model.form.VehicleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


public interface IVehicleService {

    Long cadastreVehicle(VehicleForm vehicleForm);
    Long updateVehicle(VehicleForm vehicleForm, Long vehicleId);
    void deleteVehicle(Long id);
    Page<VehicleResponse> getAllVehiclePaginated(Pageable pageable, FilterVehicle vehicle);

    VehicleResponse getVehicle(Long id);

    void saveImage(Long vehicleID, ImageForm imageForm);
    void updateImage(Long vehicleID, ImageForm imageUpdateForm);
    void deleteImage(Long vehicleId, Long imageId);

    Long countVehicles();

    SellerResponse getInfoSeller(Long vehicleId);

}
