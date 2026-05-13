package com.opscore.incident.repository;

import com.opscore.incident.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNumeroReloj(String numeroReloj);

    // Consulta corregida para evitar errores de sintaxis
    @Query("SELECT u FROM Usuario u JOIN u.especialidades e " +
            "WHERE u.rol = com.opscore.incident.enums.Rol.TECNICO " +
            "AND u.area.id = :areaId " +
            "AND e.id = :especialidadId " +
            "AND u.conectado = true " +
            "AND u.disponible = true")
    List<Usuario> findTecnicoAsignable(@Param("areaId") Long areaId,
                                       @Param("especialidadId") Long especialidadId);
}