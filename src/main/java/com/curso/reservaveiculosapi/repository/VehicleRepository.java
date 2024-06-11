package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.model.entity.UserEntity;
import com.curso.reservaveiculosapi.model.entity.VehicleEntity;
import com.curso.reservaveiculosapi.model.form.FilterVehicle;
import com.curso.reservaveiculosapi.model.projections.SellerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    @Query(nativeQuery = true,
            value = """


                    select uu.usu_tx_nome as name, uu.usu_tx_login as login , uu.usu_nr_id as id from vei_veiculo vv\s
                    join vus_veiculo_usuario vvu\s
                    on vvu.vei_nr_id = vv.vei_nr_id\s
                    join usu_usuario uu\s
                    on uu.usu_nr_id = vvu.usu_nr_id\s
                    join usp_usuario_perfil uup\s
                    on uup.usu_nr_id = uu.usu_nr_id\s
                    where uup.per_nr_id = 1
                    and vv.vei_nr_id = 31
                       """
    )
    SellerProjection findBySellerInfo(@Param("vehicleId") Long vehicleId);

    @Query(
            nativeQuery = true,
            value = """

                    select * from vei_veiculo v
            where 
            (:#{#vehicle.type() == null} or v.vei_tx_tipo = :#{#vehicle.type()})
            and (:#{#vehicle.price() == null} or v.vei_nr_preco = :#{#vehicle.price()})
            """
    )
    Page<VehicleEntity> findAllByFilters(Pageable pageable, FilterVehicle vehicle);


}
