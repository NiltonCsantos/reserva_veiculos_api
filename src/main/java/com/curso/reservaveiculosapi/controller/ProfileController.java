package com.curso.reservaveiculosapi.controller;


import com.curso.reservaveiculosapi.model.dto.ProfileResponse;
import com.curso.reservaveiculosapi.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfil")
public class ProfileController {

    private final IProfileService iProfileService;

    @GetMapping("/count")
    public ResponseEntity<Long> countProfiles(){
        return ResponseEntity.ok(iProfileService.countProfile());
    }

    @GetMapping()
    public ResponseEntity<List<ProfileResponse>> getProfiles(){
        return ResponseEntity.ok(iProfileService.getProfiles());
    }

}
