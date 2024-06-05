package com.curso.reservaveiculosapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vei_veiculo", schema = "public")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = " vei_nr_id ")
    Long id;

    @Column(name = "vei_tx_nome")
    String name;

    @Column(name = "vei_tx_marca")
    String maker;

    @Column(name = "vei_tx_tipo")
    String type;

    @Column(name = "vei_nr_preco")
    BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vehicle")
    List<VehicleUserEntity> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade = CascadeType.ALL)
    List<ImageVehicleEntity> images;
}
