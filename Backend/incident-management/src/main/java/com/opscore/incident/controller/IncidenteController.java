package com.opscore.incident.controller;

import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import com.opscore.incident.enums.TipoIncidente;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.service.IncidenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/incidentes")
@Tag(
        name = "Catálogos de incidentes",
        description = "Endpoints auxiliares para obtener tipos de incidentes y niveles de prioridad utilizados en el registro de incidentes operativos"
)
public class IncidenteController {

    private final IncidenteService incidenteService;

    @Operation(
            summary = "Listar tipos de incidente",
            description = """
                    Retorna todos los tipos de incidentes disponibles en el sistema OpsCore.
                    
                    Estos tipos se utilizan para clasificar los eventos reportados en planta,
                    facilitando el análisis de causas raíz y patrones recurrentes.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Tipos de incidente obtenidos correctamente")
    @SecurityRequirement(name = "accessCookieAuth")
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoIncidente>> listarTiposIncidente() {
        return ResponseEntity.ok(Arrays.asList(TipoIncidente.values()));
    }

    @Operation(
            summary = "Listar prioridades de incidente",
            description = """
                    Retorna los niveles de prioridad disponibles para la clasificación de incidentes.
                    
                    La prioridad se utiliza para determinar la urgencia de atención
                    y la asignación de recursos dentro del flujo operativo.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Prioridades obtenidas correctamente")
    @SecurityRequirement(name = "accessCookieAuth")
    @GetMapping("/prioridades")
    public ResponseEntity<List<Prioridad>> listarPrioridades() {
        return ResponseEntity.ok(Arrays.asList(Prioridad.values()));
    }

// solo son propuesta
//    @PostMapping
//    public Incidente reportarIncidente(@RequestBody Incidente incidente) {
//        return incidenteService.reportarIncidente(incidente);
//    }
//
//    @GetMapping("/estado/{estado}")
//    public List<Incidente> listarPorEstado(@PathVariable EstadoIncidente estado) {
//        return incidenteService.listarPorEstado(estado);
//    }
//
//    @GetMapping("/prioridad/{prioridad}")
//    public List<Incidente> listarPorPrioridad(@PathVariable Prioridad prioridad) {
//        return incidenteService.listarPorPrioridad(prioridad);
//    }
//
//    @PutMapping("/{id}/estado")
//    public Incidente actualizarEstado(@PathVariable Long id, @RequestParam EstadoIncidente nuevoEstado) {
//        return incidenteService.actualizarEstado(id, nuevoEstado);
//    }
}
