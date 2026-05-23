package com.opscore.incident.dto;

import lombok.Data;

@Data
public class ValidacionRequestDTO {

    private Long incidenteId;

    private boolean aprobado;

    private String comentario;
}
