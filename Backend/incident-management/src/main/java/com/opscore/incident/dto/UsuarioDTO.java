package com.opscore.incident.dto;

import com.opscore.incident.enums.Rol;

public record UsuarioDTO(
        Long id,
        String nombre,
        String username,
        String numeroReloj,
        Rol rol,
        boolean conectado,
        boolean disponible,
        String area
) {}