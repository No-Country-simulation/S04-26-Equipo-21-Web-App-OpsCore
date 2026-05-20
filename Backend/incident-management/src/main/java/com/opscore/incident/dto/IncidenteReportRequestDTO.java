package com.opscore.incident.dto;

import com.opscore.incident.enums.Prioridad;
import lombok.Data;
import com.opscore.incident.enums.TipoFalla;

@Data
public class IncidenteReportRequestDTO {

    private String descripcion;

    private Prioridad prioridad;

    private TipoFalla tipoFalla;

    private Long estacionId;
}
