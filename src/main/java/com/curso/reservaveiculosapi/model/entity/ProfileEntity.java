package com.curso.reservaveiculosapi.model.entity;


import com.curso.reservaveiculosapi.enuns.ProfileRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "per_perfil")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_nr_id ")
    private Long id;

    @Column(name = "per_tx_nome")
    @Enumerated(EnumType.STRING)
    private ProfileRole name;


    @ManyToMany(mappedBy = "profiles")
    private List<UserEntity> users;




}
