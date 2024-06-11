package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.model.dto.BookResponse;
import com.curso.reservaveiculosapi.model.dto.UserResponse;
import com.curso.reservaveiculosapi.model.dto.VehicleResponse;
import com.curso.reservaveiculosapi.model.entity.UserFilter;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.form.BookVehicleForm;
import com.curso.reservaveiculosapi.model.form.ProfileForm;
import com.curso.reservaveiculosapi.model.form.UserProfileForm;
import com.curso.reservaveiculosapi.model.projections.BookProjection;
import com.curso.reservaveiculosapi.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;


    @PostMapping("/profile")
    public ResponseEntity<Void> registerProfile(@RequestBody @Valid ProfileForm profileForm) {
        System.out.println(profileForm);

        userService.cadastreProfile(profileForm.getProfile());

        return ResponseEntity.status(201).build();
    }

    @GetMapping()
    public ResponseEntity<Page<UserResponse>> pageResponseEntity(Pageable pageable, UserFilter userFilter) {
        System.out.println("USERFILTER");
        System.out.println(userFilter);
            return ResponseEntity.ok(userService.getAllUserPaginated(pageable));
    }

    @PostMapping("/add-profile")
    public ResponseEntity<Void> addProfileToUser(@RequestBody @Valid UserProfileForm userProfileForm){
         userService.addProfileToUser(userProfileForm);
         return ResponseEntity.status(201).build();
    }

    @PostMapping("/reserva")
    public ResponseEntity<Void> cadastreBook(@Valid @RequestBody BookVehicleForm bookVehicleForm){
        userService.BookVehicle(bookVehicleForm);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/reserva")
    public ResponseEntity<List<VehicleResponse>> getMyReserves(){
        return ResponseEntity.ok(userService.getReserve());
    }

    @GetMapping("/reserva-info")
    public ResponseEntity<List<BookProjection>> getAllReserves(){
        return ResponseEntity.ok(userService.getAllReserves());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers(){
        return ResponseEntity.ok(userService.countUsers());
    }
}
