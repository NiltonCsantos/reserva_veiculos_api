package com.curso.reservaveiculosapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usu_usuario")
public class UserEntity implements CustomUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_nr_id")
    Long id;

    @Column(name = "usu_tx_nome")
    String name;

    @Column(name = "usu_tx_login")
    String login;

    @Column(name = "usu_tx_senha")
    String password;

    //Todo: fazer relacionamento

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usp_usuario_perfil",
            joinColumns = @JoinColumn(name = "usu_nr_id"),
            inverseJoinColumns = @JoinColumn(name = "per_nr_id"))
    private List<ProfileEntity> profiles;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user", fetch = FetchType.LAZY)
    private List<VehicleUserEntity> vehicles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();


        if (this.profiles!=null){
            profiles.
                    forEach(profileEntity ->{
                        authorities.add(new SimpleGrantedAuthority("ROLE_"+profileEntity.getName()));
                    });
        }

        return authorities;

    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
