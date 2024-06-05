package com.curso.reservaveiculosapi.model.form;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm {

    @Enumerated(EnumType.STRING)
    ProfileRole profile;

}
