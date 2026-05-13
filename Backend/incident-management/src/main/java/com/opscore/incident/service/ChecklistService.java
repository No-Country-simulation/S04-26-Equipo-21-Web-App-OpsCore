package com.opscore.incident.service;

import com.opscore.incident.model.ChecklistEjecucion;
import com.opscore.incident.repository.ChecklistEjecucionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistEjecucionRepository ejecucionRepository;
    private final IncidenteService incidenteService;

    /**
     * Procesa la inspección inicial de jornada de un operador.
     * Si se detectan fallas, dispara automáticamente un incidente crítico.
     */
    @Transactional
    public ChecklistEjecucion registrarInspeccion(ChecklistEjecucion ejecucion) {
        // 1. Guardamos la ejecución en la base de datos
        ChecklistEjecucion guardado = ejecucionRepository.save(ejecucion);

        // 2. Evaluamos si existe alguna falla en los puntos de control
        // Usamos streams para buscar si alguna respuesta tiene completado = false
        boolean tieneFallas = ejecucion.getRespuestas().stream()
                .anyMatch(respuesta -> !respuesta.isCompletado());

        // 3. Si hay fallas, delegamos la creación del incidente al servicio especializado
        if (tieneFallas) {
            incidenteService.crearIncidenteDesdeChecklist(guardado);
        }

        return guardado;
    }
}