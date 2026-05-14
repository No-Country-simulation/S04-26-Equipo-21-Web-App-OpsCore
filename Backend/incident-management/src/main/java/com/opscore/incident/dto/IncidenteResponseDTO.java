package com.opscore.incident.dto;

import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncidenteResponseDTO {
    private Long id;
    private String descripcion;
    private EstadoIncidente estado;
    private Prioridad prioridad;
    private String nombreEstacion;
    private String nombreTecnico; // Solo el nombre del técnico asignado
}