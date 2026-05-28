package com.opscore.incident.dto;

import com.opscore.incident.enums.Prioridad;
import lombok.Builder;
import lombok.Data;



import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.EstadoValidacion;
import com.opscore.incident.enums.TipoFalla;

import java.time.LocalDateTime;

@Data
@Builder
public class IncidenteResponseDTO {

    private Long id;

    private String titulo;

    private String descripcion;

    private Prioridad prioridad;

    private TipoFalla tipoFalla;

    private EstadoOperativo estadoOperativo;

    private EstadoValidacion estadoValidacion;

    private String area;

    private String estacion;

    private String tecnico;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaAsignacion;

    private LocalDateTime fechaResolucion;
}
