package com.opscore.incident.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ResolucionResponseDTO {
    private Long id;
    private Long incidenteId;
    private String descripcionSolucion;
    private LocalDateTime fechaResolucion;
}