package com.curso.reservaveiculosapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vus_veiculo_usuario")
public class VehicleUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vus_nr_id")
    Long id;

    @Column(name = "vus_dt_date")
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vei_nr_id", referencedColumnName = "vei_nr_id", nullable = false)
    VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usu_nr_id", referencedColumnName = "usu_nr_id", nullable = false)
    UserEntity user;

}
