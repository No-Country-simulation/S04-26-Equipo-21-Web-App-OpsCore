package com.opscore.incident.dto;

import java.util.Map;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenteRequestDTO {
    private String machine;      // Mapea a código de EstacionTrabajo
    private String area;         // Mapea a nombre de Area
    private String incidentType; // Convertiremos a TipoFalla (Enum)
    private String severity;     // Convertiremos a Prioridad (Enum)
    private String description;
    private Map<String, Boolean> safetyChecklist; // Captura los booleanos dinámicos del MVP
}
