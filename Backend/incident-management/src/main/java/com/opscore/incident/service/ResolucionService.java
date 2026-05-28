package com.opscore.incident.service;

import com.opscore.incident.dto.ResolucionRequestDTO;
import com.opscore.incident.dto.ResolucionResponseDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.model.*;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResolucionService {

    private final ResolucionRepository resolucionRepository;
    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;


    // INICIAR TRABAJO
    @Transactional
    public void iniciarTrabajo(Long incidenteId, Long tecnicoId) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        if (incidente.getTecnico() == null ||
                !incidente.getTecnico().getId().equals(tecnicoId)) {
            throw new RuntimeException("Técnico no autorizado");
        }

        incidente.setEstadoOperativo(EstadoOperativo.EN_PROCESO);
        incidente.setFechaInicioTrabajo(LocalDateTime.now());

        incidenteRepository.save(incidente);
    }

    // RESOLVER INCIDENTE
    @Transactional
    public ResolucionResponseDTO resolverIncidente(ResolucionRequestDTO dto) {

        Incidente incidente = incidenteRepository.findById(dto.getIncidenteId())
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        // Validar técnico asignado
        Usuario tecnico = incidente.getTecnico();

        if (tecnico == null) {
            throw new RuntimeException("Incidente sin técnico asignado");
        }

        // Cambiar estado operativo
        incidente.setEstadoOperativo(EstadoOperativo.RESUELTO);
        incidente.setFechaResolucion(LocalDateTime.now());

        // Crear resolución
        Resolucion resolucion = Resolucion.builder()
                .incidente(incidente)
                .tecnico(tecnico)
                .descripcionSolucion(dto.getDescripcionSolucion())
                .createdAt(LocalDateTime.now())
                .build();

        Resolucion guardada = resolucionRepository.save(resolucion);

        // liberar técnico
        tecnico.setDisponible(true);

        usuarioRepository.save(tecnico);
        incidenteRepository.save(incidente);

        return ResolucionResponseDTO.builder()
                .id(guardada.getId())
                .incidenteId(incidente.getId())
                .descripcionSolucion(guardada.getDescripcionSolucion())
                .fechaResolucion(incidente.getFechaResolucion())
                .build();
    }
}
