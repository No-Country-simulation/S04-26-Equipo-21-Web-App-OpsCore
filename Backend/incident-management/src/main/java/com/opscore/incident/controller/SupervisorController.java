package com.opscore.incident.controller;

import com.opscore.incident.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supervisor")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SupervisorController {

    private final SupervisorService supervisorService;

    // =========================
    // REASIGNAR TÉCNICO
    // =========================
    @PutMapping("/reasignar")
    public void reasignarTecnico(
            @RequestParam Long incidenteId,
            @RequestParam Long nuevoTecnicoId,
            @RequestParam Long supervisorId
    ) {
        supervisorService.reasignarTecnico(
                incidenteId,
                nuevoTecnicoId,
                supervisorId
        );
    }

    // =========================
    // VALIDAR INCIDENTE
    // =========================
    @PutMapping("/validar/{incidenteId}")
    public void validarIncidente(
            @PathVariable Long incidenteId,
            @RequestParam Long supervisorId
    ) {
        supervisorService.validarIncidente(
                incidenteId,
                supervisorId
        );
    }

    // =========================
    // RECHAZAR RESOLUCIÓN
    // =========================
    @PutMapping("/rechazar/{incidenteId}")
    public void rechazarResolucion(
            @PathVariable Long incidenteId,
            @RequestParam String motivo
    ) {
        supervisorService.rechazarResolucion(
                incidenteId,
                motivo
        );
    }

    // =========================
    // CERRAR INCIDENTE
    // =========================
    @PutMapping("/cerrar/{incidenteId}")
    public void cerrarIncidente(
            @PathVariable Long incidenteId,
            @RequestParam Long supervisorId
    ) {
        supervisorService.cerrarIncidente(
                incidenteId,
                supervisorId
        );
    }
}
