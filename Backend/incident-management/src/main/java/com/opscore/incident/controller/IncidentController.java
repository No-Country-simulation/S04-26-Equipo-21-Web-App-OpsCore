package com.opscore.incident.controller;

import com.opscore.incident.dto.IncidenteRequestDTO; // 👈 Cambiado al DTO del Front
import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.repository.IncidenteRepository;
import com.opscore.incident.mapper.IncidenteMapper;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.service.IncidenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidentes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IncidentController {

    private final IncidenteService incidenteService;
    private final IncidenteRepository incidenteRepository;
    private final IncidenteMapper incidenteMapper;

    // CREAR INCIDENTE (JSON DESDE EL FRONT)
    @PostMapping
    public IncidenteResponseDTO crear(@RequestBody IncidenteRequestDTO request) {
        return incidenteService.crearIncidente(request);
    }

    @GetMapping
    public List<IncidenteResponseDTO> listarTodos() {
        return incidenteRepository.findAll()
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    // Optimización usando los métodos derivados de tu repositorio
    @GetMapping("/estado/{estado}")
    public List<IncidenteResponseDTO> porEstado(@PathVariable EstadoOperativo estado) {
        return incidenteRepository.findByEstadoOperativo(estado)
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public List<IncidenteResponseDTO> porTecnico(@PathVariable Long tecnicoId) {
        return incidenteRepository.findByTecnicoId(tecnicoId)
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public IncidenteResponseDTO detalle(@PathVariable Long id) {
        Incidente incidente = incidenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));
        return incidenteMapper.toDTO(incidente);
    }
}
