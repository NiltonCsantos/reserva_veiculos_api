package com.curso.reservaveiculosapi.service;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import com.curso.reservaveiculosapi.exceptions.RegistredException;
import com.curso.reservaveiculosapi.model.dto.BookResponse;
import com.curso.reservaveiculosapi.model.dto.UserResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.entity.UserFilter;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.UserProfileForm;
import com.curso.reservaveiculosapi.model.projections.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IUserService {


   void cadastreProfile(ProfileRole profile) throws RegistredException;
   Page<UserResponse> getAllUserPaginated(Pageable pageable);

   //item 4
   void addProfileToUser(UserProfileForm userProfileForm);

   void BookVehicle(BookVehicleForm vehicleForm);

   List<VehicleResponse> getReserve();

   List<BookProjection> getAllReserves();


   void associateImageToVehicle();


}
