package com.opscore.incident.dto;

import com.opscore.incident.enums.Prioridad;
import lombok.Data;

@Data
public class IncidenteReportRequestDTO {
    private String titulo;       // Ej: "Fuga de aceite en Prensa 3"
    private String descripcion;  // Ej: "Se observa goteo constante en la manguera hidráulica principal"
    private Prioridad prioridad; // CRITICO, ALTA, MEDIA, BAJA
    private Long areaId;         // ID del área de la planta
    private Long estacionId;     // ID de la máquina o línea específica
    private Long operadorId;     // ID del operador que reporta (obtenido de su sesión)
    private Long especialidadId; // ID de la especialidad técnica requerida (Mecánica, Eléctrica, etc.)
}
