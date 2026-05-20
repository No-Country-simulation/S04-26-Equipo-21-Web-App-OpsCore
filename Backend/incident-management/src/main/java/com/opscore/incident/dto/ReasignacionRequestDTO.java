package com.opscore.incident.dto;

import lombok.Data;

@Data
public class ReasignacionRequestDTO {

    private Long incidenteId;

    private Long nuevoTecnicoId;

    private String motivo;
}
