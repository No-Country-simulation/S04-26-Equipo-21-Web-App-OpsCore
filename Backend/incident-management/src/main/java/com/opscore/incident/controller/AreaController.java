package com.opscore.incident.controller;

import com.opscore.incident.dto.AreaResponseDTO;
import com.opscore.incident.dto.EstacionTrabajoResponseDTO;
import com.opscore.incident.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Tag(
        name = "Áreas de producción",
        description = "Consulta de áreas operativas y estaciones de trabajo dentro de la planta industrial"
)
public class AreaController {

    private final AreaService areaService;

    @Operation(
            summary = "Listar áreas de la planta",
            description = """
                    Retorna todas las áreas operativas registradas en la planta industrial.
                    
                    Estas áreas representan zonas de producción donde pueden ocurrir incidentes
                    y donde se agrupan estaciones de trabajo.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Lista de áreas obtenida correctamente")
    @SecurityRequirement(name = "accessCookieAuth")
    @GetMapping
    public ResponseEntity<List<AreaResponseDTO>> listarAreas() {
        return ResponseEntity.ok(areaService.listarAreas());
    }

    @Operation(
            summary = "Listar estaciones de trabajo por área",
            description = """
                    Retorna todas las estaciones de trabajo asociadas a un área específica.
                    
                    Se utiliza para contextualizar incidentes dentro de una zona de producción
                    y facilitar la asignación de responsables.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Lista de estaciones obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Área no encontrada")
    @SecurityRequirement(name = "accessCookieAuth")
    @GetMapping("/{idArea}/estaciontrabajo")
    public ResponseEntity<List<EstacionTrabajoResponseDTO>> listarEstacionesPorArea(
            @PathVariable Long idArea
    ) {
        return ResponseEntity.ok(areaService.listarEstacionesPorArea(idArea));
    }
}
