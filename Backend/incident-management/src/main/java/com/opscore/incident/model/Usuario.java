package com.opscore.incident.model;

import com.opscore.incident.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private String username;

    @Column(unique = true)
    private String numeroReloj;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area; // Área asignada para técnicos/supervisores

    private boolean conectado;  // ¿Está en la planta? (Check-in)
    private boolean disponible; // ¿Está libre para un nuevo incidente?

    // Dentro de la clase Usuario.java
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_especialidades",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> especialidades;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.name()));
    }
}
