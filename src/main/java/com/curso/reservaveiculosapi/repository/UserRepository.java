package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.enuns.ProfileRole;
import com.curso.reservaveiculosapi.model.dto.BookResponse;
import com.curso.reservaveiculosapi.model.entity.UserEntity;
import com.curso.reservaveiculosapi.model.entity.UserFilter;
import com.curso.reservaveiculosapi.model.entity.VehicleUserEntity;
import com.curso.reservaveiculosapi.model.projections.BookProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserDetails findByLogin(String login);


    @Query(value = """

             SELECT vvu.vus_dt_date as date, COUNT(*)
            FROM vus_veiculo_usuario vvu
            JOIN usu_usuario uu
            ON uu.usu_nr_id = vvu.usu_nr_id
            JOIN usp_usuario_perfil uup
            ON uu.usu_nr_id = uup.usu_nr_id
            JOIN per_perfil pp\s
            ON pp.per_nr_id = uup.per_nr_id
            WHERE pp.per_tx_nome = 'USER'
            GROUP BY vvu.vus_dt_date
            """, nativeQuery = true)
    List<BookProjection> findReserveUsersToProfileUser(@Param("profileRole") ProfileRole profileRole);





}