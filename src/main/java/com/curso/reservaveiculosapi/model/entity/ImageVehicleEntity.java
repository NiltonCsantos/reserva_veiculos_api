package com.curso.reservaveiculosapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imv_imagen_veiculo", schema = "public")
public class ImageVehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imv_nr_id")
    Long id;

    @Column(name = "imv_tx_nome")
    String name;

    @Column(name = "imv_bt_bytes")
    private byte[] bytes;

    @Column(name = "imv_tx_extensao")
    String extension;

    //Todo: implementar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vei_nr_id", referencedColumnName = "vei_nr_id", nullable = false)
    private VehicleEntity vehicle;


}
