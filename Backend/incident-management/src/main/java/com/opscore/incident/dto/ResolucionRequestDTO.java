package com.opscore.incident.dto;

import lombok.Data;

@Data
public class ResolucionRequestDTO {
    private Long incidenteId;
    private String descripcionSolucion;
}