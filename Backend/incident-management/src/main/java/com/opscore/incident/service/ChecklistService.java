package com.opscore.incident.service;

import com.opscore.incident.dto.ChecklistRequestDTO;
import com.opscore.incident.mapper.ChecklistMapper;
import com.opscore.incident.model.*;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistEjecucionRepository ejecucionRepository;
    private final IncidenteService incidenteService;
    private final ChecklistMapper checklistMapper;

    // Repositorios necesarios para buscar por ID
    private final ChecklistRepository checklistRepository;
    private final EstacionTrabajoRepository estacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void registrarInspeccion(ChecklistRequestDTO dto) {
        // 1. Buscamos las entidades reales en la BD usando los IDs del DTO
        Checklist plantilla = checklistRepository.findById(dto.getPlantillaId())
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));

        EstacionTrabajo estacion = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new RuntimeException("Estación no encontrada"));

        Usuario operador = usuarioRepository.findById(dto.getOperadorId())
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        // 2. Usamos el Mapper para convertir el DTO en una Entidad completa
        ChecklistEjecucion ejecucion = checklistMapper.toEntity(dto, plantilla, estacion, operador);

        // 3. Guardamos la ejecución
        ChecklistEjecucion guardado = ejecucionRepository.save(ejecucion);

        // 4. Lógica de negocio: Si hay fallas, disparamos incidente
        boolean tieneFallas = dto.getRespuestas().stream()
                .anyMatch(r -> !r.isCompletado());

        if (tieneFallas) {
            incidenteService.crearIncidenteDesdeChecklist(guardado);
        }
    }
}
